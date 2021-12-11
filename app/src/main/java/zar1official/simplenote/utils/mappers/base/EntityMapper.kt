package zar1official.simplenote.utils.mappers.base

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
    fun mapFromEntityList(entityList: List<Entity>): List<DomainModel>
    fun mapToEntityList(domainList: List<DomainModel>): List<Entity>
}