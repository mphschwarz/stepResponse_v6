%creates initial pole values for a butterworth filter
function c = butterIniC(k,n,N)
if mod(n,2) == 0
	n = n/2;
	re = cos(linspace(pi/2,pi,n + 1));
	re = re(1:end-1);
	im = sin(linspace(pi/2,pi,n + 1));
	im = im(1:end-1);
	w = sqrt(re.^2 + im.^2);
	q = w./(2*abs(re));
	c=[w; q];
	c=c(:)';
	c = [k,c,zeros(1,N-1-length(c))];
else
	n = (n -1)/2;
	re = cos(linspace(pi/2,pi,n + 1));
	re = re(1:end-1);
	im = sin(linspace(pi/2,pi,n + 1));
	im = im(1:end-1);
	w = sqrt(re.^2 + im.^2);
	q = w./(2*abs(re));
	c = [w; q];
	c = c(:)';
	c = [k,c,1,zeros(1,N-2-length(c))];
end
end