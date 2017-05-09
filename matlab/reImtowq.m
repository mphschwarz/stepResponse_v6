% converts poles from [real,imag]-form to [w,q]-form
function c = reImtowq(re,im,k,N)
if mod(length(re),2) == 0
	%real = re(1:2:end);
	%imag = im(1:2:end);
	%w = sqrt(real.^2 + imag.^2);
	%q = abs(real)./(2*abs(imag));
	w = sqrt(re(1:2:end).^2 + im(1:2:end).^2);	% w = sqrt(re^2+im^2)
	q = -w./(2*re(1:2:end));
	c = [w; q]; c = c(:)';				%stripes w and q onto c
else
	%real = re(1:2:end-1);
	%imag = im(1:2:end-1);
	%w = sqrt(real.^2 + imag.^2);
	%q = abs(real)./(2*abs(imag));
	w = sqrt(re(1:2:end-1).^2 + im(1:2:end-1).^2);	% w = sqrt(re^2+im^2)
	q = -w./(2*re(1:2:end-1));
	c = [w; q]; c = c(:)'; c = [c, re(end)];	%stripes w and q onto c and adds real pole
end
	c = [k,c,zeros(1,N-length(c))];	% [k, c-vector, fills up with zeros]
end
