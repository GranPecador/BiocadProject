package com.lk.biocadproject.models

data class ParametersModel (var pressure:Double,
                            var humidity:Double,     // влажность измеряется от 0 до 100%
                            var roomTemperature:Double,
                            var workingAreaTemperature:Double,
                            var levelPH: Double,
                            var weight: Double,
                            var fluidFlow: Double,
                            var levelCO2: Double)