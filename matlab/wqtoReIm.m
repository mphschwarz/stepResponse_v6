%converts poles form [w,q]-form to [real,imag]-form
function [re,im,k] = wqtoReIm(c,n)
k = c(1);
if mod(n,2) == 0
	w = c(2:2:n + 1);
	q = c(3:2:n + 1);
	
	re = -w./(2*q);
	im = sqrt(w.^2 - re.^2);
	re = [re;re]; re = re(:)';
	im = [im;-im]; im = im(:)';
else
	w = c(2:2:n);
	q = c(3:2:n);
	
	re = -w./(2*q);
	im = sqrt(w.^2 - re.^2);
	
	re = [re;re]; re = re(:)';
	im = [im;-im]; im = im(:)';
	re = [re,-c(n+1)];
	im = [im,0];
end
end