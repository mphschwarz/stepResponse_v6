%creates initial pole values from a butterworth filter
% c: poles in wq-format
% k: scalar coefficient
% n: number of poles
% N: max number of poles
function c = butterIniC(k,n,N)
a = pi/2 + pi/2/n + pi/n*linspace(0,n-1,n);
if mod(n,2) == 0
	re = cos(a); re = [re(1:n/2); re(1:n/2)];
	re = re(:)';
	im = sin(a); im = [im(1:n/2); -im(1:n/2)];
	im = im(:)';
else
	re = cos(a); re = [re(1:(n-1)/2); re(1:(n-1)/2)];
	re = [re(:)',-1];
	im = sin(a); im = [im(1:(n-1)/2); -im(1:(n-1)/2)];
	im = [im(:)',0];
end
c = reImtowq(re,im,k,N);
end
