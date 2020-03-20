package com.lk.biocadproject.models

data class ParametersModel (var pressure:Float,
                            var humidity:Float,     // влажность измеряется от 0 до 100%
                            var roomTemperature:Float,
                            var workingAreaTemperature:Float,
                            var levelPH: Float,
                            var weight: Float,
                            var fluidFlow: Float,
                            var levelCO2: Float)