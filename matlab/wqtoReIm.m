%converts poles form [w,q]-form to [real,imag]-form
% c: poles in [w,q]-form
% n: number of poles
function [re,im,k] = wqtoReIm(c,n)
k = c(1);
if mod(n,2) == 0
	w = c(2:2:n+1);
	q = c(3:2:n+1);
	
	re = -w./(2*q);
	im = w./(2*q).*sqrt(4*q.^2 + 1);

	re = [re;re]; re = re(:)';	%stripes real values
	im = [im;-im]; im = im(:)';	%stripes imaginary values
else
	w = c(2:2:n);
	q = c(3:2:n);
	
	re = -w./(2*q);
	im = w./(2*q).*sqrt(4*q.^2 + 1);

	re = [re;re]; re = re(:)';	%stripes real values
	im = [im;-im]; im = im(:)';	%stripes imaginary values
	re = [re,-abs(c(n+1))];		%adds real pole's real value
	im = [im,0];			%adds real pole's imaginary value
end
end
