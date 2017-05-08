%rescales time vector to match base frequency of the step response
function [t,T] = normT(y,t)
x = movmean(y,100);		%smoothes data
T = 1;

[p,pi] = findpeaks(x);		%finds local maxima
[p1,p1i] = max(p);			%finds largest local maximum
[p2,p2i] = max(p(p1i+1:end));	%finds second largest local maximum
t1=t(pi(1));
t2=t(pi(2));


T = abs(t(pi(1)) - t(pi(2)));	%fundamental period
t = t/T;					%rescales time vector


%{
iy5 = 1;
while (y(iy5) < 0.5 && y(iy5+1) > 0.5) == false	%finds halfway point
	iy5 = iy5 + 1;
end

[xym, iym] = findpeaks(y(iy5:end));
iym = iym(1) + iy5;			% finds first peak in data
t = t/(t(iym) - t(iy5));		% normalizes ty
%}
end
