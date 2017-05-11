% number of samples to be shifted based on butterworth filter
% di: number of samples to shift
% dt: time to shit
% t: time vector
% y: data vector
% c: pole vector
% n: butterworth filter order
% N: maximum filter order
function [di, dt, tb] = shiftT(t,y,c,n,N)
%{
close all;
t = t10;
y = y10;
n = 6;
N = 10;
%}

tx = linspace(0, length(t)-1, length(t));	% time vector for standard butterworth
[num,den] = genFraq(butterIniC(1,n,N),n);
x = step(num,den,tx);				% generates standard butterworth step response
%[num,den] = genFraq(c,n);	% 

ix5 = 1;	% halfwaypoint butter
ixm = 1;	% peak butter

iy5 = 1;	% halfwaypoint data
iym = 1;	% peak data

while (x(ix5) < 0.5 && x(ix5+1) > 0.5) == false	% finds halfwaypoint of butter
	ix5 = ix5 + 1;
end

[~, ixm] = max(x);		% finds peak of butter
tx = tx/(tx(ixm)-tx(ix5));	% normalizes tx
tx5 = tx(ix5);			% time of halfway point in butter

while (y(iy5) < 0.5 && y(iy5+1) > 0.5) == false	% finds halfway point of data
	iy5 = iy5 + 1;
end


[~, iym] = findpeaks(y(iy5:end));	% finds finds first peak after halfwaypoint in data
if isempty(iym)			% determines if no peaks were found after halfwaypoint
	iym = length(y);	% no peaks found, so last sample is used
else
	iym = iym(1) + iy5;	% peak was found and offset added
end
ty = t/(t(iym) - t(iy5));	% normalizes ty

ty5 = ty(iy5);	% time of halfwaypoint in data

dt = ty5 - tx5;
%di = int32(dt/(ty(1) - ty(2)))
di = -int32(dt/(t(1) - t(2)));
tb = tx;
end
