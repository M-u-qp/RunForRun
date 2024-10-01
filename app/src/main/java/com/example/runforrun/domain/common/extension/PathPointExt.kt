package com.example.runforrun.domain.common.extension

import com.example.runforrun.domain.model.PathPoint

fun List<PathPoint>.lastLocationPoint() =
    findLast { it is PathPoint.LocationPoint } as? PathPoint.LocationPoint

fun List<PathPoint>.firstLocationPoint() =
    find { it is PathPoint.LocationPoint } as? PathPoint.LocationPoint