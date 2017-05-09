% number of samples to be shifted based on butterworth filter
% i0: number of samples to shift
% t0: time to shit
% t: time vector
% y: data vector
% n: butterworth filter order
% N: maximum filter order
function [di, dt] = shiftT(t,y,c,n,N)
%{
close all;
t = t10;
y = y10;
n = 6;
N = 10;
%}

tx = linspace(0, length(t)-1, length(t));	% time vector for standard butterworth
[num,den] = genFraq(butterIniC(1,n,N),n);
%[num,den] = genFraq(c,n);
x = step(num,den,tx);	% generates standard butterworth step response



ix5 = 1;	% halfwaypoint butter
ixm = 1;	% peak butter
ix0 = 1;	% start butter

iy5 = 1;	% halfwaypoint data
iym = 1;	% peak data
iy0 = 1;	% start data

while (x(ix5) < 0.5 && x(ix5+1) > 0.5) == false	% finds halfwaypoint of butter
	ix5 = ix5 + 1;
end

[~, ixm] = max(x);	% finds peak of butter
tx = tx/(tx(ixm)-tx(ix5));	% normalizes tx

tx5 = tx(ix5);
%{
txm = tx(ixm);
tx0 = tx(ix0);
%}
while (y(iy5) < 0.5 && y(iy5+1) > 0.5) == false
	iy5 = iy5 + 1;
end


[~, iym] = findpeaks(y(iy5:end));
iym = iym(1) + iy5;			% finds first peak in data
ty = t/(t(iym) - t(iy5));		% normalizes ty

ty5 = ty(iy5);
%{
tym = ty(iym);
ty0 = ty(iy0);
%}

dt = ty5 - tx5;
%di = int32(dt/(ty(1) - ty(2)))
di = -int32(dt/(t(1) - t(2)));

% plot(ty,y);
%{
hold on;
plot(tx,x);
plot(ty(1:di+end),y(1-di:end));
hold off;
%}
end
