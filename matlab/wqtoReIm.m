%converts poles form [w,q]-form to [real,imag]-form
% c: poles in [w,q]-form
% n: number of poles
function [re,im,k] = wqtoReIm(c,n)
k = c(1);
if mod(n,2) == 0
	w = c(2:2:n + 1);
	q = c(3:2:n + 1);
	
	re = -w./(2*q)
	%im = sqrt(w.^2 - re.^2)
	im = w./(2*q).*sqrt(4*q.^2 -1);
	re = [re;re]; re = re(:)';
	im = [im;-im]; im = im(:)';
else
	w = c(2:2:n);
	q = c(3:2:n);
	
	re = -w./(2*q);
	%im = sqrt(w.^2 - re.^2);
	
	im = w./(2*q).*sqrt(4*q.^2 -1);


	re = [re;re]; re = re(:)';
	im = [im;-im]; im = im(:)';
	re = [re,-abs(c(n+1))];
	im = [im,0];
end


for r=2:n+1
	roots([1,c(r)/c(r+1),c(r)^2])





end
