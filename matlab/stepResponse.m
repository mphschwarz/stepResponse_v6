function y = stepResponse(c,t,n)
den = 1;
k = c(1);		%extracts leading coefficient
c = c(2:n);		%trims off trailing unused poles
if mod(length(c),2) == 1	%determines if there is a real pole
	odd = 1;
	s = c(end); c = c(1:end-1);	%extracts trailing real pole
else
	odd = 0;
end

wp = c(1:2:end);		%extracts wp_n
qp = c(2:2:end);		%extracts qp_n

for r=1:length(wp)
	n = [1, wp(r)./qp(r), wp(r).^2];	%(s^2 + wp/qp*s + wp^2)
	den = conv(n,den);					%multiplies poles
end
num = prod(wp.^2).*k;			%numerator of tf
if odd == 1
	num = num*abs(s);			%*realPole
	den = conv(den,[1,-s]);		%*(s - sigma)
end
y = step(num,den,t);
end
