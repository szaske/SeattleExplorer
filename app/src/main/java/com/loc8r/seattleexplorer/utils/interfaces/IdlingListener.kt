/**
 * Used for testing purposes and use with Espresso's IdlingResources class
 */

package com.loc8r.seattleexplorer.utils.interfaces

interface IdlingListener {
    fun isNotIdle()
    fun isIdle()
}