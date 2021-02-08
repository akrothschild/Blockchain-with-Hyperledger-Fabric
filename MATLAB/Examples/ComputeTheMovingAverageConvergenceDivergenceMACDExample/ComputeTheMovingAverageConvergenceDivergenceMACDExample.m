%% Compute the Moving Average Convergence/Divergence (MACD)
% This example shows how to compute the MACD for Disney stock and plot the
% results.
%%

% Copyright 2015 The MathWorks, Inc.

load disney.mat
dis_CloseMACD = macd(dis); 
dis_OpenMACD = macd(dis, 'OPEN');
plot(dis_CloseMACD); 
plot(dis_OpenMACD); 
title('MACD for Disney')