package com.epam.learn.etl.process.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ETLModel {
    Integer id;
    String name;
}
