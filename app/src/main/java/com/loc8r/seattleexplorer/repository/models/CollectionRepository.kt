package com.loc8r.seattleexplorer.repository.models

import com.loc8r.seattleexplorer.domain.models.CollectionDomain

class CollectionRepository(val id: String = "",
                           val name: String = "",
                           val description: String = "",
                           val date: String = "",
                           val img_url: String = "",
                           val color: String = "",
                           val textColor: String = "#FFF")
{
    fun mapToDomain() = CollectionDomain(id,
            name,
            description,
            date,
            img_url,
            color,
            textColor)
}
