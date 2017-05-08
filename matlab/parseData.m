%extracts step response from sample
%x0: raw input data with leading and trailing noise cut out
%y0,t0: smoothed and trimmed input data
%k: step time index
function [t0,y0,x0,tstart,tend] = parseData(y,t,ep,f,tstart,tend)
x0 = y;
y = movmean(y,f);		%smoothes the data

ystart = mean(y(1:100));	%step starting val
yend = mean(y(end-100:end));	%step ending val

if tstart <= 0	%finds approximate beginning of step if tstart is unspecified
	tstart = 1;
	while abs(y(tstart)-ystart) < ep
		tstart = tstart + 1;
	end
end
ystart = mean(y(1:tstart));

if tend <= 0	%cuts off trailing noise if tend is unspecified
	tend = length(y);
	while abs(y(tend)-yend) < ep
		tend = tend - 1;
	end
end
yend = mean(y(tend:end));



d0 = ystart;
y0 = y(tstart:tend);
y0 = (y0-ones(length(y0),1)*y0(1))/(yend-y0(1));
t0 = t(tstart:tend)-ones(1,length(y0))*t(tstart);
x0 = x0(tstart:tend);
end
