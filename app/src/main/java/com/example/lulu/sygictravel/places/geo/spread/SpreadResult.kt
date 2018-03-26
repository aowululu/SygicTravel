package com.example.lulu.sygictravel.places.geo.spread

import com.example.lulu.sygictravel.places.model.Place
import java.util.LinkedList

/**
 *
 * Output of the [Spreader.spreadPlacesOnMap] spread algorithm.
 */
class SpreadResult
/**
 *
 * Outupt of the spread algorithm.
 * @param visiblePlaces Places visible on map.
 * *
 * @param hiddenPlaces Places, which are not visible.
 */
(var visiblePlaces: LinkedList<SpreadedPlace>?, var hiddenPlaces: LinkedList<Place>?)
