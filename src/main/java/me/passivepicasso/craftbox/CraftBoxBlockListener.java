package me.passivepicasso.craftbox;

import java.util.EnumSet;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.SnowFormEvent;
import org.bukkit.material.RedstoneWire;

public class CraftBoxBlockListener extends BlockListener {
    private static CraftBox plugin;

    public static void initialize( CraftBox plugin ) {
        CraftBoxBlockListener.plugin = plugin;
    }

    private HashSet<Block> checkedBlocks = new HashSet<Block>();

    @Override
    public void onBlockBreak( BlockBreakEvent event ) {
    }

    @Override
    public void onBlockBurn( BlockBurnEvent event ) {
    }

    @Override
    public void onBlockCanBuild( BlockCanBuildEvent event ) {
    }

    @Override
    public void onBlockDamage( BlockDamageEvent event ) {
    }

    @Override
    public void onBlockDispense( BlockDispenseEvent event ) {
    }

    @Override
    public void onBlockFlow( BlockFromToEvent event ) {
    }

    @Override
    public void onBlockFromTo( BlockFromToEvent event ) {
    }

    @Override
    public void onBlockIgnite( BlockIgniteEvent event ) {
    }

    @Override
    public void onBlockPhysics( BlockPhysicsEvent event ) {
    }

    @Override
    public void onBlockPlace( BlockPlaceEvent event ) {
        Block block = event.getBlock();
        if (block.getTypeId() == Material.REDSTONE_ORE.getId()) {
            int oldCurrent = 0;
            for (BlockFace facing : EnumSet.allOf(BlockFace.class)) {
                Block faced = block.getFace(facing);
                if ((faced.getTypeId() == Material.REDSTONE_WIRE.getId()) || (faced.getTypeId() == Material.REDSTONE_TORCH_ON.getId()) || (faced.getTypeId() == Material.REDSTONE_TORCH_OFF.getId())
                        || (faced.getTypeId() == Material.REDSTONE_ORE.getId())) {
                    if (faced.getTypeId() == Material.REDSTONE_WIRE.getId()) {
                        RedstoneWire rw = (RedstoneWire) faced.getState().getData();
                        oldCurrent = rw.getData() > oldCurrent ? (int) rw.getData() : oldCurrent;
                    }
                }
            }
            if (oldCurrent > 0) {
                BlockRedstoneEvent rsEvent = new BlockRedstoneEvent(block, oldCurrent, oldCurrent - 1);
                plugin.getServer().getPluginManager().callEvent(rsEvent);
            }
        }
    }

    @Override
    public void onBlockRedstoneChange( BlockRedstoneEvent event ) {
        Block block = event.getBlock();
        checkedBlocks.add(block);
        int blockCount = checkedBlocks.size();
        int oldCurrent = 0;
        if (block.getTypeId() == Material.REDSTONE_ORE.getId()) {
            for (BlockFace facing : EnumSet.allOf(BlockFace.class)) {
                Block faced = block.getFace(facing);
                if (faced.getTypeId() == Material.REDSTONE_WIRE.getId()) {
                    // RedstoneWire rw = (RedstoneWire) faced.getState().getData();
                    oldCurrent = faced.getData() > oldCurrent ? (int) faced.getData() : oldCurrent;
                }
            }
        }
        event.setNewCurrent(oldCurrent);
        for (BlockFace facing : EnumSet.of(BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP)) {
            Block faced = block.getFace(facing);
            if (checkedBlocks.contains(faced)) {
                continue;
            }
            if (!faced.isBlockIndirectlyPowered() && !faced.isBlockPowered()) {
                if (block.getTypeId() == Material.REDSTONE_WIRE.getId()) {
                    if (faced.getTypeId() == Material.REDSTONE_WIRE.getId()) {
                        continue;
                    }
                }
                if (faced.getTypeId() == Material.REDSTONE_ORE.getId()) {
                    System.out.println(event.getBlock().getType().toString());
                    if (faced.getTypeId() == Material.REDSTONE_WIRE.getId()) {
                        RedstoneWire rw = (RedstoneWire) faced.getState().getData();
                        if (rw.isPowered()) {
                            continue;
                        } else {
                            faced.setData((byte) oldCurrent);
                        }
                        BlockRedstoneEvent rsEvent = new BlockRedstoneEvent(faced, event.getOldCurrent(), event.getNewCurrent());
                        plugin.getServer().getPluginManager().callEvent(rsEvent);
                    } else {
                        BlockRedstoneEvent rsEvent = new BlockRedstoneEvent(faced, event.getOldCurrent(), event.getNewCurrent());
                        plugin.getServer().getPluginManager().callEvent(rsEvent);
                    }
                }
            }
        }
        if (blockCount == checkedBlocks.size()) {
            checkedBlocks.clear();
        }
    }

    @Override
    public void onLeavesDecay( LeavesDecayEvent event ) {
    }

    @Override
    public void onSignChange( SignChangeEvent event ) {
    }

    @Override
    public void onSnowForm( SnowFormEvent event ) {
    }

}
