%plots in- and output data and compares calculated step responses to data
function plotStep(t,x,y,t0,x0,tstart,tend,val,pmin,pmax,iter)

%plots processed input data and calculated step response
figure('Name','Simple Step Response');
plot(t,x);
hold on;
plot(t,y);
legend('x(t) (input data)','y(t) (output data)');
xlabel('time [t]');
ylabel('signal');
hold off;

%plots raw input data, processed input data and step response
figure('Name','Full Step Response')
plot(t0,x0);
hold on;
plot(t0(tstart:tend),x);
plot(t0(tstart:tend),y);
legend('x(t) (raw input)','x(t) (smoothed input)','y(t) (calculated output)');
xlabel('time [t]');
ylabel('signal');
hold off;

%plots calculated step response error for each number of poles
figure('Name','Approximation error and Iterations')
subplot(1,2,1); title('Approximation error');
plot(linspace(pmin,pmax,length(val)),val);
xlabel('number of poles');
ylabel('sum of squared differences');

subplot(1,2,2); title('Iterations');
semilogy(linspace(pmin,pmax,pmax-pmin+1),iter(pmin:pmax));
xlabel('number of poles');
ylabel('iterations');
end