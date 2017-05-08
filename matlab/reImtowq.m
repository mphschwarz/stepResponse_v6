%converts poles from [real,imag]-form to [w,q]-form
function c = reImtowq(re,im,k,N)
if mod(length(re),2) == 0
	real = re(1:2:end);
	imag = im(1:2:end);
	w = sqrt(real.^2 + imag.^2);
	q = w./(2*abs(real));
	c = [w; q]; c = c(:)';
else
	real = re(1:2:end-1);
	imag = im(1:2:end-1);
	w = sqrt(real.^2 + imag.^2);
	q = w./(2*abs(real));
	c = [w; q]; c = c(:)'; c = [c, re(end)];
end
	c = [k,c,zeros(1,N-length(c))];
end
