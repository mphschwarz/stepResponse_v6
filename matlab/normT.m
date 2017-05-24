%rescales time vector to match base frequency of the step response
function [t,T] = normT(y,t)
iy5 = 1;
while (y(iy5) < 0.5 && y(iy5+1) > 0.5) == false	%finds halfway point
	iy5 = iy5 + 1;
end

[xym, iym] = findpeaks(y(iy5:end));
if isempty(iym)
	iym = length(y);
else
	iym = iym(1) + iy5;			% finds first peak in data
end
t = t/(t(iym) - t(iy5));		% normalizes ty
T = t(iym) - t(iy5);
end
