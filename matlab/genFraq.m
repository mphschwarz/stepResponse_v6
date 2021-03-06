% [num, den]: numerator/denumerator of tf

% c: input vector in w-q-format
% n: filter order
function [num, den] = genFraq(c,n)

num = c(1);
den = 1;
if(mod(n,2) == 1)
	den = conv([1,abs(c(n+1))],den);
	n = n-1;
end
for r=2:2:n
	num = num*c(r).^2;
	den = conv([1,c(r)./c(r+1),c(r).^2],den);
end
%{
den = 1;
k = c(1); c = c(2:end);	%extracts leading coefficient
if mod(n,2) == 1	%determines if there is a real pole
	odd = 1;
	s = c(n);
	n = n-1;
	c = c(1:n);	%extracts trailing real pole
else
	odd = 0;
end
c = c(1:n);		%trims off trailing unused poles

wp = c(1:2:end);		%extracts wp_n
qp = c(2:2:end);		%extracts qp_n

for r=1:length(wp)
	t = [1, wp(r)./qp(r), wp(r).^2];	%(s^2 + wp/qp*s + wp^2)
	den = conv(t,den);			%multiplies poles
end
num = prod(wp.^2).*k;		%numerator of tf
if odd == 1
	num = num*abs(s);	%*realPole
	den = conv([1,abs(s)],den);	%*(s - sigma)
end
%}
end
