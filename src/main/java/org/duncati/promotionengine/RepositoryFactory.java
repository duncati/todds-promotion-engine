package org.duncati.promotionengine;

public enum RepositoryFactory {

    INSTANCE;

    private IRepository repository;

    public IRepository getRepository() {
        if (repository==null) {
            throw new PromotionEngineException("getRepository called without first setting the type. Define the type first via setRepositoryType");
        }
        return repository;
    }

    public void setRepositoryType(RepositoryType type) {
        switch (type) {
            case IN_MEMORY:
                repository=new InMemoryRepository();
                break;
            case DATABASE:
                repository=new DatabaseRepository();
                break;
            default:
                throw new PromotionEngineException("Illegal RepositoryType "+type+" in setRepositoryType");
        }
    }
}