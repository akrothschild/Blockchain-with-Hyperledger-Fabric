%HP-filter

%Creating variables
%Real Gross Domestic Product, Billions of Chained 2009 Dollars, Quarterly, Seasonally Adjusted Annual Rate
realgdp = [1934,5
1932,3
1930,3
1960,7
1989,5
2021,9
2033,2
2035,3
2007,5
2000,8
2022,8
2004,7
2084,6
2147,6
2230,4
2273,4
2304,5
2344,5
2392,8
2398,1
2423,5
2428,5
2446,1
2526,4
2573,4
2593,5
2578,9
2539,8
2528,0
2530,7
2559,4
2609,3
2683,8
2727,5
2764,1
2780,8
2770,0
2792,9
2790,6
2836,2
2854,5
2848,2
2875,9
2846,4
2772,7
2790,9
2855,5
2922,3
2976,6
3049,0
3043,1
3055,1
3123,2
3111,3
3119,1
3081,3
3102,3
3159,9
3212,6
3277,7
3336,8
3372,7
3404,8
3418,0
3456,1
3501,1
3569,5
3595,0
3672,7
3716,4
3766,9
3780,2
3873,5
3926,4
4006,2
4100,6
4201,9
4219,1
4249,2
4285,6
4324,9
4328,7
4366,1
4401,2
4490,6
4566,4
4599,3
4619,8
4691,6
4706,7
4736,1
4715,5
4707,1
4715,4
4757,2
4708,3
4834,3
4861,9
4900,0
4914,3
5002,4
5118,3
5165,4
5251,2
5380,5
5441,5
5411,9
5462,4
5417,0
5431,3
5378,7
5357,2
5292,4
5333,2
5421,4
5494,4
5618,5
5661,0
5689,8
5732,5
5799,2
5913,0
6017,6
6018,2
6039,2
6274,0
6335,3
6420,3
6433,0
6440,8
6487,1
6503,9
6524,9
6392,6
6382,9
6501,2
6635,7
6587,3
6662,9
6585,1
6475,0
6510,2
6486,8
6493,1
6578,2
6728,3
6860,0
7001,5
7140,6
7266,0
7337,5
7396,0
7469,5
7537,9
7655,2
7712,6
7784,1
7819,8
7898,6
7939,5
7995,0
8084,7
8158,0
8292,7
8339,3
8449,5
8498,3
8610,9
8697,7
8766,1
8831,5
8850,2
8947,1
8981,7
8983,9
8907,4
8865,6
8934,4
8977,3
9016,4
9123,0
9223,5
9313,2
9406,5
9424,1
9480,1
9526,3
9653,5
9748,2
9881,4
9939,7
10052,5
10086,9
10122,1
10208,8
10281,2
10348,7
10529,4
10626,8
10739,1
10820,9
10984,2
11124,0
11210,3
11321,2
11431,0
11580,6
11770,7
11864,7
11962,5
12113,1
12323,3
12359,1
12592,5
12607,7
12679,3
12643,3
12710,3
12670,1
12705,3
12822,3
12893,0
12955,8
12964,0
13031,2
13152,1
13372,4
13528,7
13606,5
13706,2
13830,8
13950,4
14099,1
14172,7
14291,8
14373,4
14546,1
14589,6
14602,6
14716,9
14726,0
14838,7
14938,5
14991,8
14889,5
14963,4
14891,6
14577,0
14375,0
14355,6
14402,5
14541,9
14604,8
14745,9
14845,5
14939,0
14881,3
14989,6
15021,1
15190,3
15291,0
15362,4
15380,8
15384,3
15491,9
15521,6
15641,3
15793,9
15757,6
15935,8
16139,5
16220,2
16350,0
16460,9
16527,6
16547,6
16571,6
16663,5
16778,1
16851,4
16903,2
17031,1];

%Gross Private Domestic Investment, Billions of Dollars, Quarterly, Seasonally Adjusted Annual Rate
gpdi = [
35,9
34,5
34,9
43,2
47,2
50,3
52,5
51,3
43,1
36,2
39,5
37,5
46,7
52,3
58,6
68,4
64,6
67,4
62,0
57,1
58,1
53,0
57,2
60,7
61,7
62,1
61,4
56,4
55,7
55,4
59,0
62,1
68,7
72,7
74,7
78,9
78,3
77,0
78,3
77,1
77,7
77,9
79,3
71,0
66,7
65,1
72,0
80,0
83,2
89,4
83,6
86,5
96,5
87,1
86,4
76,0
78,4
84,1
90,9
92,9
98,1
96,7
98,2
95,0
99,7
101,6
104,6
107,2
110,5
110,5
112,6
115,0
126,5
127,0
131,2
133,8
144,2
143,5
143,2
145,9
142,8
137,5
142,8
147,7
152,3
158,9
155,7
160,8
172,4
172,7
177,6
171,6
168,1
171,5
173,9
166,8
189,5
197,3
202,1
198,4
213,0
226,8
233,1
239,7
254,3
268,2
264,3
280,9
268,4
277,4
271,0
281,3
244,3
243,3
265,2
276,2
304,6
322,3
328,3
337,6
360,3
389,7
414,1
422,3
434,8
470,6
492,4
515,8
525,8
539,3
545,6
547,9
554,6
519,3
495,1
551,5
619,4
609,8
652,3
643,4
588,3
593,6
593,0
549,2
565,5
613,8
652,3
718,5
790,9
818,9
838,9
831,7
809,9
827,0
822,2
859,5
863,5
855,2
835,8
842,1
871,2
874,6
876,5
946,5
908,6
934,5
942,0
962,8
1005,4
1001,0
996,5
995,9
1010,8
1014,8
1000,7
947,6
924,6
926,6
947,5
978,8
956,8
1013,1
1024,2
1058,0
1083,9
1094,5
1095,9
1153,0
1202,1
1265,1
1251,6
1307,2
1327,3
1303,8
1303,2
1335,6
1355,2
1418,6
1474,4
1480,0
1522,0
1589,9
1625,3
1645,3
1712,3
1694,8
1739,8
1794,4
1850,6
1845,8
1890,9
1949,4
1945,9
2071,8
2055,8
2061,6
1967,5
1967,0
1937,3
1842,7
1910,0
1925,6
1927,9
1936,5
1960,2
1970,5
2048,3
2132,8
2155,2
2259,5
2311,3
2380,8
2475,2
2469,5
2527,2
2636,5
2699,7
2697,0
2684,4
2641,6
2634,2
2671,9
2658,2
2610,6
2527,0
2493,3
2435,9
2243,1
1972,1
1825,9
1786,4
1928,0
1989,5
2092,7
2164,6
2156,5
2123,5
2212,7
2228,2
2395,2
2460,8
2534,8
2529,9
2521,3
2617,6
2658,1
2750,8
2798,6
2780,7
2895,0
2992,0
2997,9
3094,6
3096,3
3115,7
3067,7
3031,6
3023,1
3048,0
3126,2
3128,7
3178,1
];

%Real Personal Consumption Expenditures, Billions of Chained 2009 Dollars, Quarterly, Seasonally Adjusted Annual Rate
rpce = [
1199,4
1219,3
1223,3
1223,6
1229,8
1244,1
1245,9
1255,8
1257,9
1277,1
1280,0
1298,8
1320,4
1342,1
1411,0
1368,4
1401,5
1361,9
1377,7
1385,8
1388,9
1416,1
1423,0
1473,3
1490,8
1499,8
1496,3
1486,4
1491,8
1511,3
1531,8
1564,0
1599,1
1629,7
1649,8
1670,5
1673,2
1678,8
1682,6
1705,8
1717,5
1720,5
1734,1
1734,9
1711,1
1725,1
1753,5
1777,1
1809,4
1837,3
1856,5
1858,6
1876,3
1900,1
1892,5
1894,9
1894,4
1922,6
1932,0
1970,7
1991,7
2016,1
2032,5
2061,3
2075,2
2095,1
2123,7
2141,4
2183,6
2222,0
2262,8
2269,2
2319,8
2345,5
2385,9
2452,9
2489,1
2495,4
2523,8
2534,2
2548,9
2583,7
2596,9
2612,7
2674,8
2715,6
2766,6
2779,1
2810,2
2828,2
2841,9
2864,6
2882,3
2895,6
2921,1
2913,1
2968,9
2996,1
3020,0
3070,2
3110,8
3170,2
3219,1
3294,6
3354,8
3353,4
3365,3
3355,5
3326,2
3337,9
3351,6
3302,5
3330,1
3385,7
3434,1
3470,5
3539,9
3572,4
3610,3
3657,5
3699,3
3719,7
3755,2
3811,8
3833,8
3915,6
3932,0
3963,5
3983,6
3981,3
4020,4
4031,2
4025,0
3934,5
3976,9
4029,6
4050,8
4050,1
4066,4
4035,9
4062,6
4077,6
4109,1
4184,1
4224,8
4308,4
4384,0
4453,1
4490,9
4554,9
4589,9
4650,6
4729,7
4774,1
4865,8
4878,3
4919,6
4974,6
5064,7
5097,1
5097,9
5168,6
5228,5
5239,5
5332,7
5371,8
5417,7
5479,7
5505,0
5530,9
5585,9
5610,5
5658,7
5676,4
5699,3
5656,2
5636,7
5684,0
5711,6
5710,1
5817,3
5857,2
5920,6
5991,1
6013,8
6067,8
6134,8
6189,1
6260,1
6308,6
6357,5
6425,9
6442,9
6500,7
6560,3
6606,4
6667,7
6740,1
6780,7
6834,0
6906,1
6937,4
7056,1
7139,9
7213,6
7341,0
7437,5
7546,8
7618,7
7731,5
7819,3
7934,1
8054,9
8132,2
8211,3
8284,4
8319,4
8340,8
8371,2
8499,1
8524,6
8568,1
8628,0
8674,4
8712,5
8809,5
8939,4
9008,8
9096,4
9155,5
9243,0
9337,8
9409,2
9511,5
9585,2
9621,3
9729,2
9781,0
9838,1
9938,4
9990,7
10024,6
10069,2
10081,8
10061,0
10077,9
10005,1
9884,7
9850,8
9806,4
9865,9
9864,8
9917,7
9998,4
10063,1
10166,1
10217,1
10237,7
10282,2
10316,8
10379,0
10396,6
10424,1
10453,2
10502,3
10523,9
10573,1
10662,2
10713,4
10805,1
10909,9
11045,2
11145,3
11227,9
11304,6
11379,3
11430,5
11537,7
11618,1
11702,1
11758,0
11853,0    
];

%Real Gross Domestic Product, Billions of Chained 2009 Dollars, Annual, Seasonally Adjusted Annual Rate
rgdp_annual = [7758
8080
8036
8736
9440
9825
10286
10227
10956
11190
11425
11341
12124
12435
12753
13532
14122
14936
15907
16956
17421
18276
18850
18888
19511
20537
21696
21584
21541
22702
23748
25069
25865
25802
26471
25965
27168
29140
30375
31442
32530
33898
35146
35820
35794
37066
38084
39622
40699
42244
44139
46104
48264
50239
50729
51635
53084
55094
56937
58455
59495
59322
57675
59135
60082
61419
62449
64053
65886
66865
]; 

%Real gross domestic product per capita, Chained 2009 Dollars, Annual, Seasonally Adjusted Annual Rate
rgdppc_annual = [53822
55083
53849
57561
61163
62568
64433
62954
66262
66484
66690
65102
68445
68794
69400
72523
74596
77820
81842
86244
87651
91041
92977
92097
93939
97828
102370
100911
99735
104093
107801
112596
114899
113303
115088
111814
115932
123265
127353
130635
133953
138321
142066
143178
141179
144266
146315
150389
152665
156619
161702
166942
172778
177896
177857
179316
182649
187862
192356
195621
197199
194794
187718
190879
192499
195366
197265
200861
205141
206759];

%Applying the HP-filter
[gdptrend, gdpcycle] = hp_filter(realgdp,1600);
[gdptrendinf, gdpcycleinf] = hp_filter(realgdp,999999999);
[gdptrend0, gdpcycle0] = hp_filter(realgdp, 0);

[gpditrend, gpdicycle] = hp_filter(gpdi,1600);
[gpditrendinf, gpdicycleinf] = hp_filter(gpdi,999999999);
[gpditrend0, gpdicycle0] = hp_filter(gpdi, 0);

[rpcetrend, rpcecycle] = hp_filter(rpce,1600);
[rpcetrendinf, rpcecycleinf] = hp_filter(rpce,999999999);
[rpcetrend0, rpcecycle0] = hp_filter(rpce, 0);

%Log
[loggdptrend, loggdpcycle] = hp_filter(log(realgdp),1600);
[loggdptrendinf, loggdpcycleinf] = hp_filter(log(realgdp),999999999);
[loggdptrend0, loggdpcycle0] = hp_filter(log(realgdp), 0);

[loggpditrend, loggpdicycle] = hp_filter(log(gpdi),1600);
[loggpditrendinf, loggpdicycleinf] = hp_filter(log(gpdi),999999999);
[loggpditrend0, loggpdicycle0] = hp_filter(log(gpdi), 0);

[logrpcetrend, logrpcecycle] = hp_filter(log(rpce),1600);
[logrpcetrendinf, logrpcecycleinf] = hp_filter(log(rpce),999999999);
[logrpcetrend0, logrpcecycle0] = hp_filter(log(rpce), 0);


%Create time axis
dates = 1947.0:0.25:2017.25;

%Plot data 
% figure;
% plot(dates, realgdp, 'k-'); hold on;
% plot(dates, trend, 'b-.'); hold on;
% plot(dates, trendinf, 'g+'); hold on;
% plot(dates, trend0, 'ro');
% 
% figure;
% plot(dates, cycle, 'r--');

% Plotting time series
figure;
    subplot(3,3,1)
        plot(dates,realgdp,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gdpcycle,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gdptrend,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Real GDP');                     %Titel of the figure
        xlabel('Quarters');                    %Labeling the x-axis
        ylabel('Billions of Dollars');         %Labeling the y-axis
        legend('Actual Time Series','Cyclical','Trend');
    subplot(3,3,2)
        plot(dates,realgdp,'k-');hold on;         %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gdpcycleinf,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gdptrendinf,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Real GDP');                        %Titel of the figure
        xlabel('Quarters');                       %Labeling the x-axis
        ylabel('Billions of Dollars');            %Labeling the y-axis
        legend('Actual Time Series','Cyclical (Lambda -> inf)','Trend (Lambda -> inf)');
    subplot(3,3,3)
        plot(dates,realgdp,'k-');hold on;       %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gdpcycle0,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gdptrend0,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Real GDP');                      %Titel of the figure
        xlabel('Quarters');                     %Labeling the x-axis
        ylabel('Billions of Dollars');          %Labeling the y-axis
        legend('Actual Time Series','Cyclical (Lambda=0)','Trend (Lambda=0)');
    subplot(3,3,4)
        plot(dates,gpdi,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gpdicycle,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gpditrend,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Gross Private Domestic Investment');                     %Titel of the figure
        xlabel('Quarters');                    %Labeling the x-axis
        ylabel('Billions of Dollars');         %Labeling the y-axis
        legend('Actual Time Series','Cyclical','Trend');
    subplot(3,3,5)
        plot(dates,gpdi,'k-');hold on;         %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gpdicycleinf,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gpditrendinf,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Gross Private Domestic Investment');                        %Titel of the figure
        xlabel('Quarters');                       %Labeling the x-axis
        ylabel('Billions of Dollars');            %Labeling the y-axis
        legend('Actual Time Series','Cyclical (Lambda -> inf)','Trend (Lambda -> inf)');
    subplot(3,3,6)
        plot(dates,gpdi,'k-');hold on;       %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gpdicycle0,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gpditrend0,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Gross Private Domestic Investment');                      %Titel of the figure
        xlabel('Quarters');                     %Labeling the x-axis
        ylabel('Billions of Dollars');          %Labeling the y-axis
        legend('Actual Time Series','Cyclical (Lambda=0)','Trend (Lambda=0)');
     subplot(3,3,7)
        plot(dates,rpce,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,rpcecycle,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,rpcetrend,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Real Personal Consumption Expenditures');                     %Titel of the figure
        xlabel('Quarters');                    %Labeling the x-axis
        ylabel('Billions of Dollars');         %Labeling the y-axis
        legend('Actual Time Series','Cyclical','Trend');
    subplot(3,3,8)
        plot(dates,rpce,'k-');hold on;         %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,rpcecycleinf,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,rpcetrendinf,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Real Personal Consumption Expenditures');                        %Titel of the figure
        xlabel('Quarters');                       %Labeling the x-axis
        ylabel('Billions of Dollars');            %Labeling the y-axis
        legend('Actual Time Series','Cyclical (Lambda -> inf)','Trend (Lambda -> inf)');
    subplot(3,3,9)
        plot(dates,rpce,'k-');hold on;       %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,rpcecycle0,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,rpcetrend0,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
        title('Real Personal Consumption Expenditures');                      %Titel of the figure
        xlabel('Quarters');                     %Labeling the x-axis
        ylabel('Billions of Dollars');          %Labeling the y-axis
        legend('Actual Time Series','Cyclical (Lambda=0)','Trend (Lambda=0)');       

%Plotting Logs
figure;
    plot(dates,100*loggdpcycle,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
    plot(dates,100*loggpdicycle,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
    plot(dates,100*logrpcecycle,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
    title('Logged Cycles');           %Titel of the figure
    xlabel('Quarters');          %Labeling the x-axis
    ylabel('Percent');       %Labeling the y-axis
    legend('Real GDP','RGPDI','RPCE');
    
%Calculating STD
std(loggdpcycle)
std(loggpdicycle)
std(logrpcecycle)

%Cutting TS RGDP
 newrgdp = realgdp (1:251,1);
 dates2 = 1947.0:0.25:2009.5;

% Applying the HP-filter to the new Time Series
 [newrgdptrend, newrgdpcycle] = hp_filter(newrgdp,1600);
 
%Log
 [lognewrgdptrend, lognewrgdpcycle] = hp_filter(log(newrgdp),1600);

%Plotting
figure;
    
        plot(dates,realgdp,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates,gdpcycle,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates,gdptrend,'b-.'); hold on;           %Plot the trend series as blue dash-dotted line ('b-.')
        plot(dates2,newrgdp,'m-', 'Linewidth', 5);hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates2,newrgdpcycle,'g--', 'Linewidth', 5); hold on;   %Plot the cyclical series as red dashed line('r--')    
        plot(dates2,newrgdptrend,'b-.','Linewidth', 5);            %Plot the trend series as blue dash-dotted line ('b-.')
        
        
        title('Real GDP');                     %Titel of the figure
        xlabel('Quarters');                    %Labeling the x-axis
        ylabel('Billions of Dollars');         %Labeling the y-axis
        legend('Actual Time Series','Cyclical','Trend', 'New TS', 'NTS Cycle', 'NTS Trend');
 
 
 
%Aufgabe 7
dates_annual1 = 1947.0:1:2016.0;
dates_annual2 = 1948.0:1:2016.0;

%Applying the HP-filter
[yT1, yC1] = hp_filter(rgdp_annual,1600);
[yT2, yC2] = hp_filter(rgdppc_annual,1600);

%Normalization
normal1 = (yT1(1:end)./yT1(1));
normal2 = (yT2(1:end)./yT2(1));

%Growth rate
growth1 = (yT1(2:end)./yT1(1:end-1)-1);
growth2 = (yT2(2:end)./yT2(1:end-1)-1);

%Plotting Normalization and Growth Rates
figure;
    subplot (1,2,1)
        plot(dates_annual1,normal1,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates_annual1,normal2,'r--'); 
        title('Normalization');           %Titel of the figure
        xlabel('Years');          %Labeling the x-axis
        ylabel('Rate');       %Labeling the y-axis
        legend('Real GDP (annual)','RGDP per capita (annual)');
    subplot (1,2,2)
        plot(dates_annual2,growth1,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
        plot(dates_annual2,growth2,'r--'); 
        title('Growth Rates');           %Titel of the figure
        xlabel('Years');          %Labeling the x-axis
        ylabel('Rate');       %Labeling the y-axis
        legend('Real GDP (annual)','RGDP per capita (annual)');