%creates initial pole values from a butterworth filter
% c: poles in wq-format
% k: scalar coefficient
% n: number of poles
% N: max number of poles
function c = butterIniC(k,n,N)
a = pi/2 + pi/2/n + pi/n*linspace(0,n-1,n);
if mod(n,2) == 0
	re = cos(a);
	re = [re(1:n/2); re(1:n/2)];	re = re(:)';	%stripes real values
	im = sin(a);
	im = [im(1:n/2); -im(1:n/2)];	im = im(:)';	%stripes imaginary values
else
	re = cos(a);
	re = [re(1:(n-1)/2); re(1:(n-1)/2)];	re = [re(:)',-1];	%stripes real values and adds real pole
	im = sin(a);
	im = [im(1:(n-1)/2); -im(1:(n-1)/2)];	im = [im(:)',0];	%stripes imaginary values and adds real pole
end
c = reImtowq(re,im,k,N);
end
