package me.passivepicasso.craftbox;

import me.passivepicasso.util.BlockMatrixNode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.material.RedstoneWire;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;

public class CraftBoxBlockListener extends BlockListener {
    private CraftBox        plugin;

    HashMap<Block, Integer> getRedstoneOre = new HashMap<Block, Integer>();

    public void initialize( CraftBox plugin ) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockRedstoneChange( BlockRedstoneEvent event ) {
        Block block = event.getBlock();
        // TypeId 74 == GLOWING_REDSTONE_ORE
        // TpyeId 73 == REDSTONE_ORE
        if ( event.getNewCurrent() == 0 && block.getTypeId() == 74 ) {
            block.setTypeId(Material.REDSTONE_ORE.getId());
        }
        int newPower = event.getNewCurrent() - (event.getNewCurrent() == 0 ? 0 : 1);

        BlockMatrixNode oreCircuit = new BlockMatrixNode(block, new HashMap<Block, BlockMatrixNode>());

        if ( block.getTypeId() == Material.REDSTONE_WIRE.getId() ) {
            block.setData((byte) event.getNewCurrent());
            HashSet<Material> filter = new HashSet<Material>();
            filter.add(Material.REDSTONE_ORE);
            filter.add(Material.GLOWING_REDSTONE_ORE);
            oreCircuit.setFilter(filter);
            for (Block eventTarget : oreCircuit.getFilteredExternalAdjacentBlocks()) {
                int oldPower = 0;
                if ( eventTarget.getState().getData() instanceof RedstoneWire ) {
                    if ( eventTarget.getBlockPower() > 0 ) {
                        continue;
                    }
                    oldPower = eventTarget.getBlockPower();
                    eventTarget.setData((byte) newPower);
                }
                plugin.getServer().getPluginManager().callEvent(new BlockRedstoneEvent(eventTarget, oldPower, newPower));
            }
        }

        if ( block.getTypeId() == 73 || block.getTypeId() == 74 ) {
            if ( event.getNewCurrent() > event.getOldCurrent() ) {
                oreCircuit.setFilter(new HashSet<Material>(EnumSet.of(Material.REDSTONE_ORE)));
                oreCircuit.floodFill();
                for (Block change : oreCircuit.getBlockMatrix()) {
                    change.setTypeId(74);
                }
            } else if ( event.getNewCurrent() != event.getOldCurrent() ) {
                oreCircuit.setFilter(new HashSet<Material>(EnumSet.of(Material.GLOWING_REDSTONE_ORE)));
                oreCircuit.floodFill();
                for (Block change : oreCircuit.getBlockMatrix()) {
                    change.setTypeId(73);
                }
            }
            oreCircuit.setFilter(EnumSet.complementOf(EnumSet.of(Material.REDSTONE_ORE, Material.GLOWING_REDSTONE_ORE)));
            for (BlockMatrixNode next : oreCircuit.getBlockMatrixNodes()) {
                for (Block eventTarget : next.getFilteredExternalAdjacentBlocks()) {
                    int oldPower = 0;
                    if ( eventTarget.getState().getData() instanceof RedstoneWire ) {
                        oldPower = eventTarget.getBlockPower();
                        eventTarget.setData((byte) newPower);
                    }
                    plugin.getServer().getPluginManager().callEvent(new BlockRedstoneEvent(eventTarget, oldPower, newPower));
                }
            }
        }
    }
    // if ( block.getState() instanceof RedstoneWire || block.getTypeId() == 73 || block.getTypeId() == 74 || block.getData() > 0 ) {
    // for (BlockFace face : EnumSet.of(BlockFace.DOWN, BlockFace.UP, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH)) {
    // Block nextBlock = block.getFace(face);
    // if ( nextBlock.getTypeId() == 73 || nextBlock.getTypeId() == 74 || nextBlock.getState() instanceof RedstoneWire ) {
    // int oldPower = 0;
    // if ( nextBlock.getTypeId() == Material.REDSTONE_ORE.getId() ) {
    // nextBlock.setTypeId(Material.GLOWING_REDSTONE_ORE.getId());
    // oldPower = 0;
    // getRedstoneOre.put(nextBlock, newPower);
    // } else if ( nextBlock.getTypeId() == Material.GLOWING_REDSTONE_ORE.getId() ) {
    // oldPower = getRedstoneOre.get(nextBlock);
    // getRedstoneOre.put(nextBlock, newPower);
    // } else {
    // oldPower = nextBlock.getData();
    // }
    // }
    // }
    // }
}
