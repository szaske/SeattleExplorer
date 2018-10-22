package com.loc8r.seattleexplorer.remote.models

import com.loc8r.seattleexplorer.repository.models.CollectionRepository

data class FireStoreCollectionsResponse(var id: String = "",
                                        val name: String = "",
                                        val description: String = "",
                                        val date: String = "",
                                        val img_url: String = "",
                                        val color: String = "",
                                        val textColor: String = "#FFF")
{

    fun withId(id: String): FireStoreCollectionsResponse{
        return FireStoreCollectionsResponse(id,
                name,
                description,
                date,
                img_url,
                color,
                textColor)
    }

    fun mapToRepository() = CollectionRepository(id,
            name,
            description,
            date,
            img_url,
            color,
            textColor)
}
