//<![CDATA[
function sj_wf(n){var t=arguments;return function(){n.apply(null,[].slice.apply(t).slice(1))}};function sj_ce(n,t,i){var r=_d.createElement(n);return t&&(r.id=t),i&&(r.className=i),r};function sj_be(n,t,i,r){if((n==_w||n==_d.body)&&t=="load"&&"undefined"!=typeof _d.readyState&&"complete"===_d.readyState){i();return}n.addEventListener?n.addEventListener(t,i,r):n.attachEvent?n.attachEvent("on"+t,i):n["on"+t]=i}function sj_ue(n,t,i,r){n.removeEventListener?n.removeEventListener(t,i,r):n.detachEvent?n.detachEvent("on"+t,i):n["on"+t]=null};function sj_jb(n,t){function r(n,t,i,u){i&&sj_ue(i,u,r),sj_evt.bind("onP1",function(){if(!n.l){n.l=1;var i=sj_ce("script");i.src=(t?"/fd/sa/"+_G.Ver:"/sa/"+_G.AppVer)+"/"+n.n+".js",sj_b.appendChild(i)}},1,5)}for(var f=arguments,e,u,i=2,o={n:n};i<f.length;i+=2)e=f[i],u=f[i+1],sj_be(e,u,sj_wf(r,o,t,e,u));i<3&&r(o,t)};function sj_ev(n){return sb_ie?event:n}function sj_et(n){return sb_ie?event.srcElement:n.target}function sj_mi(n){return sb_ie?event.fromElement:n.relatedTarget}function sj_mo(n){return sb_ie?event.toElement:n.relatedTarget};function sj_pd(n){sb_ie?event.returnValue=!1:n.preventDefault()};function sj_sp(n){sb_ie?n.cancelBubble=!0:n.stopPropagation()};function sj_we(n,t,i){while(n&&n!=(i||sj_b)){if(n==t)return!0;n=n.parentNode}return!1};function si_ct(n,t){var u="getAttribute",r,i,e;try{for(;n!==_d.body;n=n.parentNode){if(r=n.tagName=="A"&&n[u]("h")||n[u]("_ct"),r){i=n[u]("_ctf")||"si_T",_w[i]&&_w[i]("&"+r);break}if(t)break}}catch(f){}return!0}(function(){sj_be(_d,"mousedown",function(n){si_ct(sb_ie?event.srcElement:n.target)},0)})();function sj_gx(){return sb_i6?new ActiveXObject("MSXML2.XMLHTTP"):new XMLHttpRequest};function sj_go(n,t,i){for(var r=0;n.offsetParent&&n!=(i||sj_b);)r+=n["offset"+t],n=n.offsetParent;return r};(function(){function i(){--r<1&&u.fire("onP1")}var r,n=0,u=sj_evt,t=[];for(_w.si_PP&&t.push("onPP");n<t.length;n++)u.bind(t[r=n],i,1);!n&&i()})();function lb(){function e(n){i=n[1],u.unbind(f,e)}var u=sj_evt,f="onSbBusy",i,n,r,t;for(u.bind(f,e,1),n=1;n<4;++n)r="sb_form"+(n>1?n:""),t=_ge(r),!t||i&&_ge(r+"_q")==i||!t.reset||t.reset();_w.si_sendCReq&&sb_st(si_sendCReq,800),_w.lbc&&lbc()}function init(){}(function(){var n=sj_evt,i="onSbBusy",t=null;n.bind(i,function(){sb_ct(t),t=sb_st(sj_wf(n.fire,"onSbIdle"),1100)}),sj_be(_d,"keydown",function(t){n.fire(i,sj_et(t))})})(),sj_evt.bind("onHTML",function(){var n=_ge("sb_form");n&&sj_be(n,"submit",sj_wf(sj_evt.fire,"onSearch",n))});
//]]>