package me.passivepicasso.craftbox;

public class RecipeState {

    private Recipes.Result result;

    private String         recipeMethod;

    boolean                recipeState = false;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RecipeState other = (RecipeState) obj;
        if (result != other.result) {
            return false;
        }
        return true;
    }

    /**
     * @return the recipeMethod
     */
    public String getRecipeMethod() {
        return recipeMethod;
    }

    /**
     * @return the recipeState
     */
    public boolean getRecipeState() {
        return recipeState;
    }

    /**
     * @return the id
     */
    public Recipes.Result getResult() {
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        return result;
    }

    /**
     * @param recipeMethod
     *            the recipeMethod to set
     */
    public void setRecipeMethod( String recipeMethod ) {
        this.recipeMethod = recipeMethod;
    }

    /**
     * @param recipeState
     *            the recipeState to set
     */
    public void setRecipeState( boolean recipeState ) {
        this.recipeState = recipeState;
    }

    /**
     * @param result
     *            the id to set
     */
    public void setResult( Recipes.Result result ) {
        this.result = result;
    }

}
