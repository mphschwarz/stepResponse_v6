% s: step index

% x: data input vector
% t: time input vector
% n: filter order
% start: user defined approx. start
function s = findI(x,t,n,start)

[xxmax, sxmax] = findpeaks(x(start:end)); xxmax = xxmax(1); sxmax = sxmax(1);	%first peak after start



end
