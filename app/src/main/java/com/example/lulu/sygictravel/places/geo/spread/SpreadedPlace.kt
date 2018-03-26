package com.example.lulu.sygictravel.places.geo.spread

import android.graphics.Point

import com.example.lulu.sygictravel.places.model.Place

/**
 *
 * Wrapper of [Place], which has been already processed by the spread algorithm.
 */
class SpreadedPlace
/**
 *
 * Wrapper of [Place], which has been already processed by the spread algorithm.
 * @param place A processed place.
 * *
 * @param canvasCoords Place's `x, y` coordinates on map's canvas on display.
 * *
 * @param sizeConfig Place's [SpreadSizeConfig].
 */
(var place: Place?, var canvasCoords: Point?, var sizeConfig: SpreadSizeConfig?)
