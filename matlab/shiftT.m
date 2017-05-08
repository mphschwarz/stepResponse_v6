% number of samples to be shifted based on butterworth filter
% i0: number of samples to shift
% t0: time to shit
% t: time vector
% y: data vector
% n: butterworth filter order
% N: maximum filter order
function [i0, t0] = shiftT(t,y,n,N)

tx = linspace(0, length(t) - 1, length(t));	% time vector for standard butterworth
[num,den] = genFraq(butterIniC(1,n,N),n);
x = step(num,den,tx);	% generates standard butterworth step resonpse

ix5 = 1;	% halfwaypoint butter
ixm = 1;	% peak butter
ix0 = 1;	% start butter

iy5 = 1;	% halfwaypoint data
iym = 1;	% peak data
iy0 = 1;	% start data


for i=1:length(t)	% finds halfway point of butter
	if x(i) < 0.5 && x(i+1) > 0.5
		ix5 = i;
	end
end
[xm, ixm] = max(x);	% finds peak of butter
tt = tx/(ixm-ix5);	% normalizes tx

for i=1:length(t)	% finds halfway point in data
	if y(i) < 0.5 && y(i+1) > 0.5
		iy5 = i;
	end
end
[xym, iym] = findpeaks(y(iy5,end)); iym = iym(1) + iy5;	% finds first peak in data



end
