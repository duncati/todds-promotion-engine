package org.duncati.promotionengine;

import static org.duncati.promotionengine.RepositoryType.IN_MEMORY;

/**
 * Singleton Factory for the repository (data store).
 */
public enum RepositoryFactory {

    INSTANCE;

    private IRepository repository;

    /**
     * Gets the repository whose type was configured by calling setRepositoryType (defaults to IN_MEMORY).
     * @return the repository
     */
    public IRepository getRepository() {
        if (repository==null) {
            setRepositoryType(IN_MEMORY);
        }
        return repository;
    }

    /**
     * Defines what repository type the getRepository method will return. At the time of this writing the options are:
     * IN_MEMORY & DATABASE. IN_MEMORY is a temporary non-persistent in memory store, DATABASE is for a repository
     * backed by an actual database (the DATABASE repository is not yet implemented).
     * @param type Either IN_MEMORY or DATABASE
     */
    public void setRepositoryType(RepositoryType type) {
        switch (type) {
            case IN_MEMORY:
                repository=new InMemoryRepository();
                break;
            case DATABASE:
                repository=new DatabaseRepository();
                break;
            default:
                throw new RuntimeException("Illegal RepositoryType "+type+" in setRepositoryType");
        }
    }
}