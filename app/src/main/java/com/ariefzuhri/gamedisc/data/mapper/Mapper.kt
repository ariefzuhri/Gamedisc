package com.ariefzuhri.gamedisc.data.mapper

interface Mapper<DTO, DomainModel> {

    fun map(input: DTO): DomainModel
}