var bWidth = 30;
function  init_chart(xData,yData,echartId,title,legend,color,echartType,rangeFloorNum,dataFmatter){
    var barChart = echarts.init(document.getElementById(echartId));
    var yFm = '{value}';
    var xFm = '{c}';
    if(dataFmatter!=undefined){
        yFm = '{value}'+dataFmatter;
        xFm = '{c}'+dataFmatter;
    }
    var baroption = {
        title: {
            text: title
        },
        // tooltip: {
        //     trigger: 'axis'
        // },
        tooltip: {
            trigger: 'item',
            formatter: function(params) {
                return toolTip(params,yData);
            }
        },
        legend: {
            data: legend
        },
        grid: {
            x: 30,
            x2: 40,
            y2: 24,
            containLabel: true
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: xData
            }
        ],
        yAxis: [
            {
                type: 'value',
                min: function(value) {
                    return mixValue(value,rangeFloorNum);
                },
                axisLabel: {
                    formatter: yFm
                }
            }
        ],
        color:color,
        series: [
            {
                name: legend,
                type: echartType,
                data: yData,
                itemStyle : {
                    normal: {
                        label: {
                            show: true,
                            formatter: xFm
                        }
                    }
                },
                barWidth: bWidth,//柱状图长度
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },

            }
        ]
    };
    barChart.setOption(baroption);

    window.onresize = barChart.resize;

}

function  init_chartWith2(xData,yData,echartId,title,legend,color,echartType,xFmatter,yFmatter){
    var barChart = echarts.init(document.getElementById(echartId));
    var yFm = '{value}';
    var xFm1 = '{c}';
    var xFm2 = '{c}';
    if(xFmatter!=undefined){
        yFm = '{value}'+xFmatter;
        xFm1 = '{c}'+yFmatter[0];
        xFm2 = '{c}'+yFmatter[1];
    }
    var baroption = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: legend
        },
        grid: {
            x: 30,
            x2: 40,
            y2: 24,
            containLabel: true
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: xData
            }
        ],
        yAxis: [
            {
                //name: legend,
                type: 'value',
                axisLabel: {
                    formatter: yFm
                }
            }
        ],
        color:color,
        series: [
            {
                name: legend[0],
                type: echartType[0],
                data: yData[0],
                itemStyle : {
                    normal: {
                        label: {
                            show: true,
                            formatter: xFm1
                        }
                    }
                },
                barWidth: bWidth,//柱状图长度
                yAxisIndex: 0,
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },

            },{
                name: legend[1],
                type: echartType[1],
                data: yData[1],
                itemStyle : {
                    normal: {
                        label: {
                            show: true,
                            formatter: xFm2
                        }
                    }
                },
                barWidth: bWidth,//柱状图长度
                yAxisIndex: 0,
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },

            }
        ]
    };
    barChart.setOption(baroption);

    window.onresize = barChart.resize;

}

function  init_chartW2W2Y(xData,yData,echartId,title,legend,color,echartType,rangeFloorNum,xFm,yFm){
    yFm = yFm==undefined?["",""]:yFm;
    yFm[0] = yFm[0]==undefined?"":yFm[0];
    yFm[1] = yFm[1]==undefined?"":yFm[1];
    xFm = xFm==undefined?["",""]:xFm;
    xFm[0] = xFm[0]==undefined?"":xFm[0];
    xFm[1] = xFm[1]==undefined?"":xFm[1];
    var barChart = echarts.init(document.getElementById(echartId));
    var baroption = {
        title: {
            text: title
        },
        backgroundColor: 'white',
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: legend
        },
        grid: {
            x: 30,
            x2: 40,
            y2: 24,
            containLabel: true
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: xData
            }
        ],
        yAxis: [
            {
                //name: legend[0],
                type: 'value',
                axisLabel: {
                    formatter: '{value}'+xFm[0]
                },
                min: function(value) {
                    return mixValue(value,rangeFloorNum[0]);
                },
            },
            {
                //name: legend[1],
                type: 'value',
                axisLabel: {
                    formatter: '{value}'+xFm[1]
                },
                min: function(value) {
                    return mixValue(value,rangeFloorNum[1]);
                },
            }
        ],
        color:color,
        series: [
            {
                name: legend[0],
                type: echartType[0],
                data: yData[0],
                itemStyle : {
                    normal: {
                        label : {
                            show: true,
                            formatter: '{c}'+yFm[0],
                        }
                    }
                },
                barWidth: bWidth,//柱状图长度
                yAxisIndex: 0,
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },

            },{
                name: legend[1],
                type: echartType[1],
                data: yData[1],
                itemStyle : {
                    normal: {
                        label : {
                            show: true,
                            formatter: '{c}'+yFm[1],
                        }
                    }
                },
                barWidth: bWidth,//柱状图长度
                yAxisIndex: 1,
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },

            }
        ]
    };
    barChart.setOption(baroption);

    window.onresize = barChart.resize;

}

function mixValue(value,rangeFloorNum) {
    //if(rangeFloorNum==2) alert(Math.floor(value.min));
    if(rangeFloorNum==-1||rangeFloorNum==undefined||value.min==0) return 0;
    else return value.min - rangeFloorNum;
}

function toolTip(params,data){
    // 先拼接当期数值，以及 '，环比增长' 文字
    relVal = params.data;
    gap = params.data - data[params.dataIndex - 1];
    // 再拼接环比增长率及百分号，或者 '未知' 文字
    relVal += params.dataIndex === 0 ? '' : ",增长值为："+gap+"，环比增长"+((gap * 100) / data[params.dataIndex - 1]).toFixed(2) + '%';
    return relVal;
}