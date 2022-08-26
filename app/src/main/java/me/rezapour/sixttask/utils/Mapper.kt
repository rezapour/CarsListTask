package me.rezapour.sixttask.utils

interface Mapper<Entity,Domain> {

    fun entityToDomain(entity: Entity):Domain

}