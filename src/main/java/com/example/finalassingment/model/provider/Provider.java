package com.example.finalassingment.model.provider;

import com.example.finalassingment.model.User;

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Provider extends User {

    public Provider(GenericProviderBuilder builder) {
        super(builder);
    }

    public Provider() {
    }

    public static abstract class GenericProviderBuilder<T extends GenericProviderBuilder<T>> extends GenericUserBuilder<T> {
        public abstract Provider build();
    }
}
