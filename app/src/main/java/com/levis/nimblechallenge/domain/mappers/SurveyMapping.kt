package com.levis.nimblechallenge.domain.mappers

import com.levis.nimblechallenge.core.utils.parse
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity
import com.levis.nimblechallenge.data.local.database.entity.SurveyRemoteKeyEntity
import com.levis.nimblechallenge.data.network.dtos.SurveyDto
import com.levis.nimblechallenge.data.network.response.SurveyListResponse
import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import com.levis.nimblechallenge.domain.model.survey.SurveyPageModel

fun SurveyDto?.toModel(): SurveyModel {
    return SurveyModel(
        id = this?.id ?: "",
        title = this?.title ?: "",
        description = this?.description ?: "",
        coverImageUrl = this?.coverImageUrl ?: "",
        createdAt = this?.createdAt?.parse()
    )
}

fun SurveyDto.toEntity(): SurveyEntity = SurveyEntity(
    id = this.id ?: "",
    title = title ?: "",
    description = description ?: "",
    coverImageUrl = coverImageUrl ?: "",
    createdAt = createdAt.toString()
)

fun SurveyModel.toEntity() = SurveyEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    coverImageUrl = this.coverImageUrl,
    createdAt = this.createdAt.toString()
)

fun SurveyEntity.toModel(): SurveyModel = SurveyModel(
    id = id,
    title = title,
    description = description,
    coverImageUrl = coverImageUrl,
    createdAt = createdAt.parse()
)

fun List<SurveyModel>.toSurveyEntities() = this.map { surveyModel ->
    surveyModel.toEntity()
}

fun SurveyListResponse.toSurveyPageModel() = SurveyPageModel(
    page = meta?.page ?: 0,
    totalPages = meta?.pages ?: 0,
    surveyList = data?.map { data -> data.attributes.toModel() } ?: listOf(),
)

fun SurveyDto.toSurveyKeyEntity(nextPage: Int?) = SurveyRemoteKeyEntity(
    id = this.id ?: "",
    nextPage = nextPage,
    createdAt = this.createdAt?.parse()?.time
)

fun List<SurveyDto>.toSurveyKeyEntities(nextPage: Int?) = this.map { surveyModel ->
    surveyModel.toSurveyKeyEntity(nextPage)
}