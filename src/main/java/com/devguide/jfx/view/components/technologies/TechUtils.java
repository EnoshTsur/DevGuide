package com.devguide.jfx.view.components.technologies;

import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.util.stream.Collectors;

/***
 * Front Utils : Styles..
 */
public interface TechUtils {

    /***
     * Front list
     */
    List<String> backTechnologies = List.of(
            "Spring boot",
            "Vavr",
            "Apache VM",
            "Polopoly"
    );

    /***
     * Front list
     */
    List<String> frontTechnologies = List.of(
            "React",
            "GraphQL",
            "Grid",
            "Fela"
    );


}
