%rescales time vector to match base frequency of the step response
function [t,T] = normT(x0,t0)
x = movmean(x0,100);		%smoothes data
[p,pi] = findpeaks(x);		%finds local maxima
[p1,p1i] = max(p);			%finds largest local maximum
[p2,p2i] = max(p(p1i+1:end));	%finds second largest local maximum
t1=t0(pi(1));
t2=t0(pi(2));

T = abs(t0(pi(1)) - t0(pi(2)));	%fundamental period
t = t0/T;					%rescales time vector
end