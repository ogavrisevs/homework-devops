package com.neueda.homework.devops.infrastructure;

interface ResourceFactory {

    <T> T create(String endpoint, Class<T> resourceClass);

}
