package zar1official.simplenote.data.mappers.base

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun mapFromEntityList(entityList: List<Entity>): List<DomainModel> =
        entityList.map { mapFromEntity(it) }

    fun mapToEntityList(domainList: List<DomainModel>): List<Entity> =
        domainList.map { mapToEntity(it) }
}