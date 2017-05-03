%creates initial pole values from a butterworth filter
% c: poles in wq-format
% k: scalar coefficient
% n: number of poles
% N: length of c-vector
function c = butterIniC(k,n,N)
if mod(n,2) == 0
	re = cos(linspace(pi/2,3*pi/2,n)); re = [re(1:n/2); re(1:n/2)];
	re = re(:)';
	im = sin(linspace(pi/2,3*pi/2,n)); im = [im(1:n/2); -im(1:n/2)];
	im = im(:)';

	c = reImtowq(re,im,k,N);
else
	re = cos(linspace(pi/2,3*pi/2,n)); re = [re(1:(n-1)/2); re(1:(n-1)/2)];
	re = [re(:)',-1];
	im = sin(linspace(pi/2,3*pi/2,n)); im = [im(1:(n-1)/2); -im(1:(n-1)/2)];
	im = [im(:)',0];
	
	c = reImtowq(re,im,k,N);
end
end
