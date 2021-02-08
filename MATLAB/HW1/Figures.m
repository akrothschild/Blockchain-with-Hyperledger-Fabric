%% Script to produce figures for time series

%% Define time units
% Here: quarterly data

dates=1947.0:0.25:2017.25;

%% Plot time series

figure;
plot(dates,y,'k-');hold on;      %Plot the original time series as black solid line('k-') % hold on ensures that the next plot is shown in the same figure
plot(dates,yC,'r--'); hold on;   %Plot the cyclical series as red dashed line('r--')    
plot(dates,yT,'b-.');            %Plot the trend series as blue dash-dotted line ('b-.')
title('Investment');           %Titel of the figure
xlabel('Quarters');          %Labeling the x-axis
ylabel('Billions of Dollars')%Labeling the y-axis
legend('Actual Time Series','Cyclical','Trend')

