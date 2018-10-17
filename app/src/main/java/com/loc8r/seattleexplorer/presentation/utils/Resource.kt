package com.loc8r.seattleexplorer.presentation.utils

class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val message: String?)