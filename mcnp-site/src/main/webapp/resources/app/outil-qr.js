document
	.write("<style>#swirl_icon{width: 60px;height: 60px;}#bouton_qr_launcher{background:#aa325f;width:150px;float:right;margin:10px}.status_confirmation_icon{box-sizing:border-box;position:absolute;top:50%;left:50%;width:50px;height:50px;margin-top:-50px;margin-left:-25px}.cam_video{width:400px;height:300px;background:#F3F3F3;border:1px solid #F3F3F3}#confirmation_box{border:1px solid #c3c3c3;padding:15px;border-radius:10px;height:200px;position:relative}@keyframes minispinner{to{transform:rotate(360deg)}}.qrmodal{display:none;position:fixed;z-index:1;left:0;top:0;width:100%;height:100%;overflow:auto;background-color:#000;background-color:rgba(0,0,0,.4)}.qrmodal-content{background-color:#fefefe;margin:15% auto;padding:20px;border:1px solid #888;width:450px;z-index:-99999}#minispinner:before,.spinner:before{content:'';box-sizing:border-box;top:50%;left:50%;border-radius:50%;animation:spinner 3s linear infinite}#close-qrmodal{color:#aaa;float:right;font-size:28px;font-weight:700}#close-qrmodal:focus,#close-qrmodal:hover{color:#000;text-decoration:none;cursor:pointer}#minispinner:before{position:absolute;width:50px;height:50px;margin-top:-50px;margin-left:-25px;border:5px solid #ccc;border-top-color:#aa325f}#bloc_step_1,.code_qr{height:200px}#bloc_step_2{display:none;margin:0 -50% 0 0;position:absolute;top:50%;left:50%;transform:translate(-50%,-50%)}#button_authenticate{border-radius:10px;background:#aa325f;color:#fff}#conteneur_2{position:relative}.code_qr{width:200px;display:block;margin:20px 20px 100px}@keyframes spinner{to{transform:rotate(360deg)}}.spinner:before{position:absolute;width:100px;height:100px;margin-top:-50px;margin-left:-50px;border:5px solid #ccc;border-top-color:#aa325f}#conteneur_loader{display:inline-block}#qr_code_operateur{width:200px;height:200px;background:#C3C3C3;display:none}.loader,.loader:after{border-radius:50%;width:100px;height:100px}.loader{margin:60px auto;font-size:10px;position:relative;text-indent:-9999px;border-top:10px solid rgba(170,50,95,.2);border-right:10px solid rgba(170,50,95,.2);border-bottom:10px solid rgba(170,50,95,.2);border-left:10px solid #aa325f;-webkit-transform:translateZ(0);-ms-transform:translateZ(0);transform:translateZ(0);-webkit-animation:load8 1.1s infinite linear;animation:load8 1.1s infinite linear}@-webkit-keyframes load8{0%{-webkit-transform:rotate(0);transform:rotate(0)}100%{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}@keyframes load8{0%{-webkit-transform:rotate(0);transform:rotate(0)}100%{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}.overlayDiv{position:fixed;top:0;left:0;width:100%;height:100%;z-index:99}.svg-box{position:absolute;top:50%;left:50%;width:50px;height:50px;margin-top:-50px;margin-left:-25px}.green-stroke{stroke:#7CB342}.red-stroke{stroke:#FF6245}.yellow-stroke{stroke:#FFC107}.circular circle.path{stroke-dasharray:330;stroke-dashoffset:0;stroke-linecap:round;opacity:.4;animation:.7s draw-circle ease-out}.checkmark{stroke-width:6.25;stroke-linecap:round;position:absolute;top:56px;left:49px;width:52px;height:40px}.checkmark path{animation:1s draw-check ease-out}@keyframes draw-circle{0%{stroke-dasharray:0,330;stroke-dashoffset:0;opacity:1}80%{stroke-dasharray:330,330;stroke-dashoffset:0;opacity:1}100%{opacity:.4}}@keyframes draw-check{0%{stroke-dasharray:49,80;stroke-dashoffset:48;opacity:0}50%{stroke-dasharray:49,80;stroke-dashoffset:48;opacity:1}100%{stroke-dasharray:130,80;stroke-dashoffset:48}}</style>");
var checkIconb64 = 'data:image/svg+xml;utf8;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pgo8IS0tIEdlbmVyYXRvcjogQWRvYmUgSWxsdXN0cmF0b3IgMTkuMC4wLCBTVkcgRXhwb3J0IFBsdWctSW4gLiBTVkcgVmVyc2lvbjogNi4wMCBCdWlsZCAwKSAgLS0+CjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeD0iMHB4IiB5PSIwcHgiIHZpZXdCb3g9IjAgMCA1MDQuMTIgNTA0LjEyIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA1MDQuMTIgNTA0LjEyOyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgd2lkdGg9IjUxMnB4IiBoZWlnaHQ9IjUxMnB4Ij4KPGNpcmNsZSBzdHlsZT0iZmlsbDojM0RCMzlFOyIgY3g9IjI1Mi4wNiIgY3k9IjI1Mi4wNiIgcj0iMjUyLjA2Ii8+CjxwYXRoIHN0eWxlPSJmaWxsOiMzN0ExOEU7IiBkPSJNNDYzLjE2MywxMTQuNjA5TDI0MC4yNDYsMzQ1LjQwM2wwLjM5NCwyNC44MTJoMTAuMjRsMjQxLjQyOC0xOTQuNTYgIEM0ODUuMjE4LDE1My45OTQsNDc1LjM3MiwxMzMuMTIsNDYzLjE2MywxMTQuNjA5eiIvPgo8cGF0aCBzdHlsZT0iZmlsbDojRjJGMUVGOyIgZD0iTTQ5OS4zOTcsMTAzLjU4MmwtNDQuNTA1LTQ0LjExMWMtNS45MDgtNS45MDgtMTUuNzU0LTUuOTA4LTIyLjA1NSwwTDI0Mi42MDksMjU2bC04Mi4zMTQtODEuMTMyICBjLTUuOTA4LTUuOTA4LTE1Ljc1NC01LjkwOC0yMi4wNTUsMGwtMzkuMzg1LDM4Ljk5MWMtNS45MDgsNS45MDgtNS45MDgsMTUuNzU0LDAsMjEuNjYyTDIzMC40LDM2NS44ODMgIGMzLjU0NSwzLjU0NSw4LjI3MSw0LjcyNiwxMi45OTcsNC4zMzJjNC43MjYsMC4zOTQsOS40NTItMC43ODgsMTIuOTk3LTQuMzMybDI0My4wMDMtMjQwLjI0NiAgQzUwNS4zMDUsMTE5LjMzNSw1MDUuMzA1LDEwOS40ODksNDk5LjM5NywxMDMuNTgyeiIvPgo8cGF0aCBzdHlsZT0iZmlsbDojRTZFNUUzOyIgZD0iTTI1Ni4zOTQsMzY1Ljg4M2wyNDMuMDAzLTI0MC4yNDZjNS45MDgtNS45MDgsNS45MDgtMTUuNzU0LDAtMjEuNjYybC03LjA4OS02LjY5NUwyNDMuMDAzLDM0Mi4yNTIgIEwxMDUuMTU3LDIwNy45NTFsLTUuOTA4LDUuOTA4Yy01LjkwOCw1LjkwOC01LjkwOCwxNS43NTQsMCwyMS42NjJsMTMxLjU0NSwxMzAuMzYzYzMuNTQ1LDMuNTQ1LDguMjcxLDQuNzI2LDEyLjk5Nyw0LjMzMiAgQzI0OC4xMjMsMzcwLjYwOSwyNTIuODQ5LDM2OS40MjgsMjU2LjM5NCwzNjUuODgzeiIvPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8L3N2Zz4K';
var checkIcon = "<img class='status_confirmation_icon' src='" + checkIconb64
	+ "' />";
var alertIconb64 = 'data:image/svg+xml;utf8;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pgo8IS0tIEdlbmVyYXRvcjogQWRvYmUgSWxsdXN0cmF0b3IgMTkuMC4wLCBTVkcgRXhwb3J0IFBsdWctSW4gLiBTVkcgVmVyc2lvbjogNi4wMCBCdWlsZCAwKSAgLS0+CjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeD0iMHB4IiB5PSIwcHgiIHZpZXdCb3g9IjAgMCAyODYuMDU0IDI4Ni4wNTQiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDI4Ni4wNTQgMjg2LjA1NDsiIHhtbDpzcGFjZT0icHJlc2VydmUiIHdpZHRoPSI1MTJweCIgaGVpZ2h0PSI1MTJweCI+CjxnPgoJPHBhdGggc3R5bGU9ImZpbGw6I0UyNTc0QzsiIGQ9Ik0xNDMuMDI3LDBDNjQuMDQsMCwwLDY0LjA0LDAsMTQzLjAyN2MwLDc4Ljk5Niw2NC4wNCwxNDMuMDI3LDE0My4wMjcsMTQzLjAyNyAgIGM3OC45OTYsMCwxNDMuMDI3LTY0LjAyMiwxNDMuMDI3LTE0My4wMjdDMjg2LjA1NCw2NC4wNCwyMjIuMDIyLDAsMTQzLjAyNywweiBNMTQzLjAyNywyNTkuMjM2ICAgYy02NC4xODMsMC0xMTYuMjA5LTUyLjAyNi0xMTYuMjA5LTExNi4yMDlTNzguODQ0LDI2LjgxOCwxNDMuMDI3LDI2LjgxOHMxMTYuMjA5LDUyLjAyNiwxMTYuMjA5LDExNi4yMDkgICBTMjA3LjIxLDI1OS4yMzYsMTQzLjAyNywyNTkuMjM2eiBNMTQzLjAzNiw2Mi43MjZjLTEwLjI0NCwwLTE3Ljk5NSw1LjM0Ni0xNy45OTUsMTMuOTgxdjc5LjIwMWMwLDguNjQ0LDcuNzUsMTMuOTcyLDE3Ljk5NSwxMy45NzIgICBjOS45OTQsMCwxNy45OTUtNS41NTEsMTcuOTk1LTEzLjk3MlY3Ni43MDdDMTYxLjAzLDY4LjI3NywxNTMuMDMsNjIuNzI2LDE0My4wMzYsNjIuNzI2eiBNMTQzLjAzNiwxODcuNzIzICAgYy05Ljg0MiwwLTE3Ljg1Miw4LjAxLTE3Ljg1MiwxNy44NmMwLDkuODMzLDguMDEsMTcuODQzLDE3Ljg1MiwxNy44NDNzMTcuODQzLTguMDEsMTcuODQzLTE3Ljg0MyAgIEMxNjAuODc4LDE5NS43MzIsMTUyLjg3OCwxODcuNzIzLDE0My4wMzYsMTg3LjcyM3oiLz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8Zz4KPC9nPgo8L3N2Zz4K';
var alertIcon = "<img class='status_confirmation_icon' src='" + alertIconb64
	+ "' />";
var warningIconb64 = "data:image/svg+xml;utf8;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pgo8IS0tIEdlbmVyYXRvcjogQWRvYmUgSWxsdXN0cmF0b3IgMjEuMC4wLCBTVkcgRXhwb3J0IFBsdWctSW4gLiBTVkcgVmVyc2lvbjogNi4wMCBCdWlsZCAwKSAgLS0+CjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgdmVyc2lvbj0iMS4xIiBpZD0iQ2FwYV8xIiB4PSIwcHgiIHk9IjBweCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIiB3aWR0aD0iNTEycHgiIGhlaWdodD0iNTEycHgiPgo8bGluZWFyR3JhZGllbnQgaWQ9IlNWR0lEXzFfIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSIgeDE9IjEyNy43MjA3IiB5MT0iMjk2LjQwNDYiIHgyPSI3MDkuNTAwNyIgeTI9Ii0yODUuMzc1NCIgZ3JhZGllbnRUcmFuc2Zvcm09Im1hdHJpeCgxLjAwMzkgMCAwIC0xLjAwMzkgMC4xOTIgNTE2LjU1ODEpIj4KCTxzdG9wIG9mZnNldD0iMCIgc3R5bGU9InN0b3AtY29sb3I6I0ZGQjkyRCIvPgoJPHN0b3Agb2Zmc2V0PSIxIiBzdHlsZT0ic3RvcC1jb2xvcjojRjU5NTAwIi8+CjwvbGluZWFyR3JhZGllbnQ+CjxwYXRoIHN0eWxlPSJmaWxsOnVybCgjU1ZHSURfMV8pOyIgZD0iTTQzNy4yOSw1MTEuOTAzSDc0LjcwN2MtNTUuNDY0LDAtOTEuNTM4LTU4LjM2OS02Ni43MzQtMTA3Ljk3N0wxODkuMjY0LDQxLjM0MSAgYzI3LjQ5Ni01NC45OTIsMTA1Ljk3Mi01NC45OTIsMTMzLjQ2OCwwbDE4MS4yOTEsMzYyLjU4NEM1MjguODI5LDQ1My41MzQsNDkyLjc1NSw1MTEuOTAzLDQzNy4yOSw1MTEuOTAzeiIvPgo8bGluZWFyR3JhZGllbnQgaWQ9IlNWR0lEXzJfIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSIgeDE9IjIwNC45NTE5IiB5MT0iMjE5LjE3NTciIHgyPSIzODAuMzIxOSIgeTI9IjQzLjgxNTciIGdyYWRpZW50VHJhbnNmb3JtPSJtYXRyaXgoMS4wMDM5IDAgMCAtMS4wMDM5IDAuMTkyIDUxNi41NTgxKSI+Cgk8c3RvcCBvZmZzZXQ9IjAiIHN0eWxlPSJzdG9wLWNvbG9yOiNGRkI5MkQiLz4KCTxzdG9wIG9mZnNldD0iMSIgc3R5bGU9InN0b3AtY29sb3I6I0Y1OTUwMCIvPgo8L2xpbmVhckdyYWRpZW50Pgo8cGF0aCBzdHlsZT0iZmlsbDp1cmwoI1NWR0lEXzJfKTsiIGQ9Ik00MzcuMjksNTExLjkwM0g3NC43MDdjLTU1LjQ2NCwwLTkxLjUzOC01OC4zNjktNjYuNzM0LTEwNy45NzdMMTg5LjI2NCw0MS4zNDEgIGMyNy40OTYtNTQuOTkyLDEwNS45NzItNTQuOTkyLDEzMy40NjgsMGwxODEuMjkxLDM2Mi41ODRDNTI4LjgyOSw0NTMuNTM0LDQ5Mi43NTUsNTExLjkwMyw0MzcuMjksNTExLjkwM3oiLz4KPGxpbmVhckdyYWRpZW50IGlkPSJTVkdJRF8zXyIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiIHgxPSIxOS44NTUyIiB5MT0iMjU5LjU0MDkiIHgyPSI0ODkuNzYxOSIgeTI9IjI1OS41NDA5IiBncmFkaWVudFRyYW5zZm9ybT0ibWF0cml4KDEuMDAzOSAwIDAgLTEuMDAzOSAwLjE5MiA1MTYuNTU4MSkiPgoJPHN0b3Agb2Zmc2V0PSIwIiBzdHlsZT0ic3RvcC1jb2xvcjojRkZGNDY1Ii8+Cgk8c3RvcCBvZmZzZXQ9IjEiIHN0eWxlPSJzdG9wLWNvbG9yOiNGRkU2MDAiLz4KPC9saW5lYXJHcmFkaWVudD4KPHBhdGggc3R5bGU9ImZpbGw6dXJsKCNTVkdJRF8zXyk7IiBkPSJNNDg2LjA2NSw0MTIuOTA0TDMwNC43NzQsNTAuMzJjLTkuNDM4LTE4Ljg3Ni0yNy42NzItMzAuMTQ1LTQ4Ljc3NS0zMC4xNDUgIHMtMzkuMzM3LDExLjI2OS00OC43NzUsMzAuMTQ1TDI1LjkzMiw0MTIuOTA0Yy04LjUxNCwxNy4wMjgtNy42MjIsMzYuODYzLDIuMzg3LDUzLjA1N2MxMC4wMDksMTYuMTk1LDI3LjM1MSwyNS44NjQsNDYuMzg4LDI1Ljg2NCAgaDM2Mi41ODRjMTkuMDM3LDAsMzYuMzc5LTkuNjY5LDQ2LjM4OC0yNS44NjRDNDkzLjY4Nyw0NDkuNzY3LDQ5NC41OCw0MjkuOTMzLDQ4Ni4wNjUsNDEyLjkwNHogTTQ2Ni42LDQ1NS40MDUgIGMtNi4zMjQsMTAuMjMyLTE3LjI4LDE2LjM0MS0yOS4zMDgsMTYuMzQxSDc0LjcwN2MtMTIuMDI4LDAtMjIuOTg1LTYuMTA5LTI5LjMwOC0xNi4zNDFjLTYuMzI0LTEwLjIzMS02Ljg4OC0yMi43NjMtMS41MDgtMzMuNTIyICBMMjI1LjE4Miw1OS4zYzYuMDUyLTEyLjEwNCwxNy4yODMtMTkuMDQ2LDMwLjgxNi0xOS4wNDZzMjQuNzY1LDYuOTQyLDMwLjgxNiwxOS4wNDZsMTgxLjI5MSwzNjIuNTg0ICBDNDczLjQ4Niw0MzIuNjQyLDQ3Mi45MjMsNDQ1LjE3NCw0NjYuNiw0NTUuNDA1eiIvPgo8bGluZWFyR3JhZGllbnQgaWQ9IlNWR0lEXzRfIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSIgeDE9IjQyMS4zMjY1IiB5MT0iMTA0LjQ1MDQiIHgyPSIxMzcuMjM2NSIgeTI9IjM4OC41NDAzIiBncmFkaWVudFRyYW5zZm9ybT0ibWF0cml4KDEuMDAzOSAwIDAgLTEuMDAzOSAwLjE5MiA1MTYuNTU4MSkiPgoJPHN0b3Agb2Zmc2V0PSIwIiBzdHlsZT0ic3RvcC1jb2xvcjojQkUzRjQ1O3N0b3Atb3BhY2l0eTowIi8+Cgk8c3RvcCBvZmZzZXQ9IjEiIHN0eWxlPSJzdG9wLWNvbG9yOiNCRTNGNDUiLz4KPC9saW5lYXJHcmFkaWVudD4KPHBhdGggc3R5bGU9ImZpbGw6dXJsKCNTVkdJRF80Xyk7IiBkPSJNNDM3LjI4Niw1MTEuODk5aC0yOC41NTFMMjQzLjcwMSwzNDYuODY1Yy00Ljg3OS0yLjM4OS03LjMyOS02LjUxNS03LjMyOS0xMi40MDggIGMwLTIyLjI2Ny0wLjkxNC0zOC4xNDktMi43NDEtODguNTQ2Yy0xLjgyNy01MC4zODctMi43NDEtNjAuNzM3LTIuNzQxLTgzLjAwNGMwLTcuMTA4LDIuNDE5LTEyLjU4OSw3LjI1OC0xNi40NTQgIGM0LjgyOS0zLjg3NSwxMS4wOTMtNS44MDMsMTguNzgzLTUuODAzczEzLjM0MiwyLjAwOCwxNi45MzYsNi4wMzRjMC42OTMsMC43NjMsMTUxLjE0LDE1MS4xNiwyMDMuMDcyLDIwMy4wNzJsMjcuMDg2LDU0LjE3MSAgQzUyOC44MzMsNDUzLjUzMSw0OTIuNzUzLDUxMS44OTksNDM3LjI4Niw1MTEuODk5eiIvPgo8bGluZWFyR3JhZGllbnQgaWQ9IlNWR0lEXzVfIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSIgeDE9IjMyOS4zMzkyIiB5MT0iMzMuMjkzMSIgeDI9IjIzNi42NjkyIiB5Mj0iMTI1Ljk3MzEiIGdyYWRpZW50VHJhbnNmb3JtPSJtYXRyaXgoMS4wMDM5IDAgMCAtMS4wMDM5IDAuMTkyIDUxNi41NTgxKSI+Cgk8c3RvcCBvZmZzZXQ9IjAiIHN0eWxlPSJzdG9wLWNvbG9yOiNCRTNGNDU7c3RvcC1vcGFjaXR5OjAiLz4KCTxzdG9wIG9mZnNldD0iMSIgc3R5bGU9InN0b3AtY29sb3I6I0JFM0Y0NSIvPgo8L2xpbmVhckdyYWRpZW50Pgo8cGF0aCBzdHlsZT0iZmlsbDp1cmwoI1NWR0lEXzVfKTsiIGQ9Ik0zOTYuOTY5LDUxMS44OTloLTc0LjY3MWwtODUuNjA0LTg1LjYwNGMtNS42OTItNS4wMi04LjU0My0xMC44NzItOC41NDMtMTcuNTY5ICBjMC02Ljk3NywyLjg1MS0xMi45MSw4LjU0My0xNy43ODljNS42OTItNC44NzksMTIuNjA5LTcuMzE5LDIwLjc0MS03LjMxOWM3LjE1OCwwLDEzLjM1MiwyLjQ0LDE4LjU3Miw3LjMxOUwzOTYuOTY5LDUxMS44OTl6Ii8+CjxwYXRoIHN0eWxlPSJmaWxsOiNGRkZGRkY7IiBkPSJNMjI4LjE1NCw0MDguNzIyYzAtNi45NzcsMi44NDYtMTIuOTAzLDguNTM3LTE3Ljc4MWM1LjY5Mi00Ljg4LDEyLjYxLTcuMzI2LDIwLjc0MS03LjMyNiAgYzcuMTYxLDAsMTMuMzUxLDIuNDQ2LDE4LjU3MSw3LjMyNmM1LjIyMSw0Ljg3OCw3Ljg0LDEwLjgwNCw3Ljg0LDE3Ljc4MWMwLDYuNjk2LTIuNjE3LDEyLjU1My03Ljg0LDE3LjU3MyAgYy01LjIyLDUuMDIyLTExLjQxMSw3LjUzMi0xOC41NzEsNy41MzJjLTguMTMyLDAtMTUuMDQ5LTIuNTExLTIwLjc0MS03LjUzMkMyMzEsNDIxLjI3NSwyMjguMTU0LDQxNS40MTksMjI4LjE1NCw0MDguNzIyeiAgIE0yMzAuODkzLDE2Mi45MDhjMC03LjEwNSwyLjQxNy0xMi41OTMsNy4yNTQtMTYuNDU5YzQuODM1LTMuODY2LDExLjA5Ny01LjgwMSwxOC43ODYtNS44MDFzMTMuMzM4LDIuMDEzLDE2Ljk0LDYuMDM1ICBjMy42MDEsNC4wMjIsNS40MDYsOS40MjksNS40MDYsMTYuMjI1YzAsMjIuMjY5LTAuMzg2LDMyLjYxMy0xLjE0MSw4My4wMDhjLTAuNzYzLDUwLjM5Ny0xLjE0LDY2LjI3Ny0xLjE0LDg4LjUzNyAgYzAsNC42NDUtMi4xNzUsOC4yODEtNi41MTksMTAuOTA1Yy00LjM1MSwyLjYyNS04Ljg2NiwzLjkzOS0xMy41NDUsMy45MzljLTEzLjcwNywwLTIwLjU2Mi00Ljk0NC0yMC41NjItMTQuODQ0ICBjMC0yMi4yNi0wLjkxNC0zOC4xNC0yLjczOS04OC41MzdDMjMxLjgwNSwxOTUuNTIsMjMwLjg5MywxODUuMTc2LDIzMC44OTMsMTYyLjkwOHoiLz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPGc+CjwvZz4KPC9zdmc+Cg==";
var warningIcon = "<img class='status_confirmation_icon' src='"
	+ warningIconb64 + "' />";

var iconeQr = '';

// XHRFactory.release()
var XHRFactory = (function() {
	// static private member
	var stack = new Array();
	var poolSize = 10;
	var nullFunction = function() {
	}; // for nuking the onreadystatechange

	// private static methods

	function createXHR() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject('Microsoft.XMLHTTP')
		}
	}

	// cache a few for use
	for (var i = 0; i < poolSize; i++) {
		stack.push(createXHR());
	}

	// shared instance methods
	return ({
		release: function(xhr) {
			xhr.onreadystatechange = nullFunction;
			stack.push(xhr);
		},
		getInstance: function() {
			if (stack.length < 1) {
				return createXHR();
			} else {
				return stack.pop();
			}
		},
		toString: function() {
			return "stack size = " + stack.length;
		}
	});
})();

function initiateqr(idBoutonSubmit, idChampCM, obligation) {
	var xhttpV = new XMLHttpRequest();
	var modal = document.getElementById('myModal');
	var champCm = document.getElementById('button_qr_code');
	modal.style.display = "block";
	startQRCameraMode2(idBoutonSubmit, idChampCM, obligation);
	document.getElementById('close-qrmodal').addEventListener('click',
		function() {
			modal.style.display = "none";
			qr.stop();
		}, false);
}

function initiateqr2() {
	var xhttpV = new XMLHttpRequest();
	var modal = document.getElementById('myModal');
	var champCm = document.getElementById('button_qr_code');
	modal.style.display = "block";
	startQRCamera()
	document.getElementById('close-qrmodal').addEventListener('click',
		function() {
			modal.style.display = "none";
			qr.stop();
		}, false);

}

function initiateqrbio() {
	var xhttpV = new XMLHttpRequest();
	startQRCamera();
}

function startQRCamera(qrcam) {
	'use strict';
	var qrcam = new QCodeDecoder();
	var modal = document.getElementById('myModal');
	var champCm = document.getElementById('button_qr_code');
	modal.style.display = "block";
	document.getElementById('close-qrmodal').addEventListener('click',
		function() {
			modal.style.display = "none";
			qr.stop();
		}, false);
	if (!(qrcam.isCanvasSupported() && qrcam.hasGetUserMedia())) {
		alert('Your browser doesn\'t match the required specs.');
		throw new Error('Canvas and getUserMedia are required');
	}
	var video = document.querySelector('video');
	var reset = document.querySelector('#reset');
	var stop = document.querySelector('#stop');

	/*
	 * function resultHandler(err, result) {
	 * 
	 * if (err) return console.log(err.message);
	 *  // alert("result"); var str = result; var infosqr = str.split(',');
	 * document.getElementById('code_membre').value = '' + infosqr[0];
	 * document.getElementById('code_membre_qr_auth').value = '' + infosqr[0];
	 * modal.style.display = "none"; qrcam.stop();
	 *  } qrcam.decodeFromCamera(video, resultHandler);
	 */

	function resultHandler(err, result) {
		if (err) {
			return console.log(err.message);
		}
		qrcam.stop();

		var str = result;
		var infosqr = str.split(',');
		document.getElementById('code_membre').value = '' + infosqr[0];
		document.getElementById('code_membre_qr_auth').value = '' + infosqr[0];
		modal.style.display = "none";

	}

	// prepare a canvas element that will receive
	// the image to decode, sets the callback for
	// the result and then prepares the
	// videoElement to send its source to the
	// decoder.

	qrcam.decodeFromCamera(video, resultHandler, true);

}

function startQRCameraMode2(idBoutonSubmit, idChampCM, obligation) {
	'use strict';
	var qr = new QCodeDecoder();
	if (!(qr.isCanvasSupported() && qr.hasGetUserMedia())) {
		alert('Your browser doesn\'t match the required specs.');
		throw new Error('Canvas and getUserMedia are required');
	}

	var video = document.querySelector('video');
	var reset = document.querySelector('#reset');
	var stop = document.querySelector('#stop');

	function resultHandler(err, result) {

		if (err) {
			qr.stop();
			return console.log(err.message);
		}

		var str = result;
		var infosqr = str.split(',');
		qr.stop();

		// alert("resultat");
		if (infosqr[0].length != 20) {
			alert("Ce code membre est incorret !");
		} else {
			// alert("Code membre obtenu");
			document.getElementById('myModal').innerHTML = '<div id="minispinner"></div>';
			member(infosqr[0], idBoutonSubmit, idChampCM, obligation);
		}
	}

	qr.decodeFromCamera(video, resultHandler, true);
}

function getBaseUrl() {
	var url;
	var origin = document.location.origin;
	if (origin.search("localhost") > 0) {
		url = "https://prod.gacsource.net";
	} else if (origin.search("tom") > 0) {
		url = origin.replace("tom", "prod");
	} else {
		url = origin;
	}
	console.log(url);
	return url;
}

function createPreConfirmation(idBouton, messageConfirmation, code_operateur) {
	var xhttpC = new XMLHttpRequest();
	var code_membre = document.getElementById('code_membre_qr_auth').value;
	var code_membre_auteur = "";

	if (code_operateur.length != 20) {
		alert("Ce code membre est incorret ! " + code_operateur.length);
	} else {

		document.getElementById('bloc_step_1').style.display = "none";
		document.getElementById('bloc_step_2').style.display = "block";

		xhttpC.onreadystatechange = function() {

			if (this.readyState == 4 && this.status == 200) {

				var responseObj = JSON.parse(this.responseText);
				// alert("response"+this.responseText);
				if (responseObj.status == 1) {

					verificationPeriodique(responseObj.idVerif, idBouton);
					notififyMember(responseObj.idVerif, messageConfirmation);
				}
			}
		};

		xhttpC.open("POST", document.location.origin + "/jmcnp/confirmation/request",
			true);
		xhttpC.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
		xhttpC.send("codeMembre=" + code_membre + "&codeOperateur="
			+ code_operateur + "&activite=" + window.location.href
			+ "&message=" + messageConfirmation + "&_csrf=" + $('input[name="_csrf"]').val());
	}

}

function createPreConfirmationSpecifique(idBouton, messageConfirmation,
	code_operateur) {
	var xhttpC = new XMLHttpRequest();
	var code_membre = code_operateur;
	var code_membre_auteur = "";

	if (code_operateur.length != 20) {
		alert("Ce code membre est incorret ! " + code_operateur.length);
	} else {

		document.getElementById('bloc_step_1').style.display = "none";
		document.getElementById('bloc_step_2').style.display = "block";

		xhttpC.onreadystatechange = function() {

			console.log(this.responseText);
			if (this.readyState == 4 && this.status == 200) {
				var responseObj = JSON.parse(this.responseText);
				// alert("response"+this.responseText);
				if (responseObj.status == 1) {

					notififyMemberSpecifique(responseObj.id_verif, code_membre,
						messageConfirmation);
					verificationPeriodique(responseObj.id_verif, idBouton);
				}
			}
		};

		xhttpC.open("POST", getBaseUrl() + "/confirmation/request",
			true);
		xhttpC.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
		xhttpC.send("code_membre=" + code_membre + "&code_operateur="
			+ code_operateur + "&activite=" + window.location.href
			+ "&message_confirmation=" + messageConfirmation);
	}

}

function notififyMemberSpecifique(id_confirmation, code_membre, message) {

	// var code_membre = document.getElementById('code_membre_qr_auth').value;
	// var code_membre_auteur = "";
	// alert("envoi de la notification a "+code_membre);
	var xhttpN = new XMLHttpRequest();
	xhttpN.onreadystatechange = function() {
		var code_membre = document.getElementById('code_membre_qr_auth').value;
		var code_membre_auteur = "";
		// alert("reponse notif status"+this.status);
		if (this.readyState == 4 && this.status == 200) {
		}
	};
	xhttpN
		.open(
			"POST",
			getBaseUrl() + "/confirmation/secureconfirmationmessage",
			true);
	xhttpN
		.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttpN.send("code_membre=" + code_membre
		+ "&nom_operateur=Nom_test&code_operateur=" + code_membre
		+ "&message_confirmation=" + message + "&id_confirmation="
		+ id_confirmation);
}
function notififyMember(id_confirmation, message) {

	var code_membre = document.getElementById('code_membre_qr_auth').value;
	var code_membre_auteur = "";
	// alert("envoi de la notification a "+code_membre);
	var xhttpN = new XMLHttpRequest();
	xhttpN.onreadystatechange = function() {
		var code_membre = document.getElementById('code_membre_qr_auth').value;
		var code_membre_auteur = "";
		// alert("reponse notif status"+this.status);
		if (this.readyState == 4 && this.status == 200) {
		}
	};
	xhttpN
		.open(
			"POST",
			document.location.origin + "/jmcnp/confirmation/secureconfirmationmessage",
			true);
	xhttpN
		.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttpN.send("codeMembre=" + code_membre
		+ "&nomOperateur=ESMC&codeOperateur=" + code_membre
		+ "&message=" + message + "&id="
		+ id_confirmation + "&_csrf=" + $('input[name="_csrf"]').val());
}

function member(code_membre, idBoutonSubmit, idChampCM, obligation) {
	var xhttpI = new XMLHttpRequest();
	xhttpI.onreadystatechange = function() {
		// console.log(this.responseText);
		if (xhttpI.readyState == 4 && xhttpI.status == 200) {
			var responseObjI = JSON.parse(this.responseText);
			if (responseObjI.codeMembre.length == 20) {
				document.getElementById('myModal').style.display = "none";
				console.log(this.responseText);

				if (responseObjI.codeStatus == 0) {
					if (document.getElementById(idChampCM)) {
						document.getElementById(idChampCM).value = responseObjI.codeMembre;
					}

					if (document.getElementById('code_membre')) {
						document.getElementById('code_membre').value = responseObjI.codeMembre;
						document.getElementById('code_membre').focus();
					}

					if (document.getElementById('code_membre_qr_auth')) {
						document.getElementById('code_membre_qr_auth').value = responseObjI.codeMembre;
					}

					// Si c'est une personne physique
					if (responseObjI.codeMembre.slice(-1) == "P") {
						if (document.getElementById('nom_membre')) {
							document.getElementById('nom_membre').value = responseObjI.nomMembre;
						}
						if (document.getElementById('prenom_membre')) {
							document.getElementById('prenom_membre').value = responseObjI.prenomMembre;
						}
					} else {
						if (document.getElementById('nom_membre')) {
							document.getElementById('nom_membre').value = responseObjI.nomMembre
								+ " " + responseObjI.raisonSociale;
						}
						if (document.getElementById('raison_sociale')) {
							document.getElementById('raison_sociale').value = responseObjI.raisonSociale;
						}
					}

					if (obligation == true) {
						document.getElementById(idBoutonSubmit).disabled = false;
					}
				} else {
					alert("Votre compte a été désactivé. Veuillez activer votre compte afin de pouvoir faire cette opération.");
					console.log(responseObjI.message);
				}

			}
		}
	}
	xhttpI.open("GET", document.location.origin + "/jmcnp/recherche/membre?codeMembre=" + code_membre, true);
	xhttpI.send();
}

function verificationPeriodique(idVerif, idboutonlock) {
	// alert("idboutonlock --"+idboutonlock);
	console.log("Verfication de " + idVerif)
	var xhttpV = XHRFactory.getInstance();
	var url = document.location.origin + "/jmcnp/confirmation/check?id=" + idVerif;

	xhttpV.open("GET", url, true);
	/*xhttpV
			.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");*/

	xhttpV.onreadystatechange = function() {
		if (xhttpV.readyState == 4) {
			// if "OK"
			if (xhttpV.status == 200) {
				// process the response
				var responseObjV = JSON.parse(xhttpV.responseText);

				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					document.getElementById('status_check').innerHTML = "<br/><center>En attente de la confirmation du membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						verificationPeriodique(idVerif, idboutonlock);
					}, 3000);
				}

				if (responseObjV.status == "2") {
					document.getElementById(idboutonlock).disabled = false;
					document.getElementById('minispinner').style.display = "none";
					document.getElementById('status_icon').innerHTML = checkIcon;
					document.getElementById('status_check').innerHTML = "<br/><center><strong>Confirmation éffectuée !</strong><br/> Veuillez procéder à la suite de votre opération.</center>";
					XHRFactory.release(xhttpV);
				}
				if (responseObjV.status == "3") {

					document.getElementById('status_icon').innerHTML = alertIcon;
					document.getElementById('minispinner').style.display = "none";
					document.getElementById('status_check').innerHTML = "<br/><center><strong>La confirmation a été déclinée par le membre.</strong><br/> Impossible de continuer l'opération en cours.</center>";
					XHRFactory.release(xhttpV);
				}
			}

		}
	};

	xhttpV.send();

	/*
	 * var xhttpV = new XMLHttpRequest(); xhttpV.onreadystatechange = function() {
	 * if (this.readyState == 4 && this.status == 200) { setTimeout(function() {
	 * verificationPeriodique(idVerif, idboutonlock); }, 2000);
	 *  } else {} };
	 */

}

function outilqrbio(idFormulaire, idDiv, idBouton, messageConfirmation,
	code_operateur) {

	document.getElementById(idBouton).disabled = true;
	var message = messageConfirmation;
	var lock = idBouton;
	var formulaire = document.getElementById(idFormulaire, idDiv);
	var confirmationBox = document.getElementById(idDiv);
	var divModal = document.createElement("div");
	divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div id="qrmodal-content" class="qrmodal-content"><span id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR du membre en façe de votre Caméra afin de l\'authentifier.</p><video class="cam_video" autoplay></video><br/></div></div>';
	var divCaptcha = document.createElement("div");
	divCaptcha.innerHTML = '<br/><div id="confirmation_box"><div id="bloc_step_1"><p>Veuillez entrer çi dessous le Code membre du Membre afin de lui envoyer une demande d\'authentification</p><div class="input-group mb-3" id="champ_code_membre"><input type="text" id="code_membre_qr_auth" class="form-control" placeholder="Votre code membre" aria-label="Votre code membre" aria-describedby="button_qr_code"><div class="input-group-append"><button class="btn btn-outline-primary" type="button" id="button_qr_code">QR Code</button></div></div><button class="btn btn-outline-secondary" type="button" id="button_authenticate">Demander la confirmation</button></div><div id="bloc_step_2"><div id="minispinner"></div><div id="status_icon"></div><br/><br/><p id="status_check">Veuillez patienter</p></div></div><br/>';
	// prepend theKid to the beginning of theParent
	// formulaire.insertBefore(buttonQR, formulaire.firstChild);
	formulaire.insertBefore(divModal, formulaire.firstChild);
	confirmationBox.insertBefore(divCaptcha, formulaire.confirmationBox);
	document.getElementById('button_authenticate').addEventListener(
		'click',
		function() {
			// alert("button_authenticate");
			var code_membre = document
				.getElementById('code_membre_qr_auth').value;
			createPreConfirmation(idBouton, messageConfirmation,
				code_operateur);
			// modal.style.display = "none";
			// qr.stop();
		}, false);
	// insertAfter(buttonQR, formulaire);
	document.getElementById('button_qr_code').addEventListener('click',
		function() {
			// modal.style.display = "block";
			initiateqr2();
			// alert('Hello');
			// startQRCamera();
		}, false);
}

function outil_confirmation_biometrique_personelle(idFormulaire, idDiv,
	idBouton, messageConfirmation, code_operateur) {

	document.getElementById(idBouton).disabled = true;
	var message = messageConfirmation;
	var lock = idBouton;
	var formulaire = document.getElementById(idFormulaire, idDiv);
	var confirmationBox = document.getElementById(idDiv);
	// var divModal = document.createElement("div");
	// divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div
	// id="qrmodal-content" class="qrmodal-content"><span
	// id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR
	// du membre en façe de votre Caméra afin de l\'authentifier.</p><video
	// class="cam_video" autoplay></video><br/></div></div>';
	var divCaptcha = document.createElement("div");
	divCaptcha.innerHTML = '<br/><div id="confirmation_box"><div id="bloc_step_1"><center><p>En attente de votre confirmation biométrique</p><img src="/design/swirl.gif" id="swirl_icon"/><br/><br/><button class="btn btn-outline-secondary" type="button" id="button_authenticate">Confirmer cette opération</button></div><div id="bloc_step_2"><div id="minispinner"></div><div id="status_icon"></div><br/><br/><p id="status_check">Veuillez patienter</p></div></div><br/>';

	// formulaire.insertBefore(divModal, formulaire.firstChild);
	confirmationBox.insertBefore(divCaptcha, formulaire.confirmationBox);
	document.getElementById('button_authenticate').addEventListener(
		'click',
		function() {
			// alert("button_authenticate");
			// alert("stage 0");
			var code_membre = code_operateur;

			// alert("stage 1 "+code_operateur);

			// alert("stage 2 "+code_membre);
			createPreConfirmationSpecifique(idBouton, messageConfirmation,
				code_operateur);
			// modal.style.display = "none";
			// qr.stop();
		}, false);

}

function outilqr(idform) {
	// '<button class="btn btn-primary">QR CODE</button>';
	var formulaire = document.getElementById(idform);
	var buttonQR = document.createElement("button");
	buttonQR.className = "btn btn-primary"
	buttonQR.innerHTML = 'QR CODE';
	buttonQR.setAttribute("id", "bouton_qr_launcher");
	var divModal = document.createElement("div");
	divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div id="qrmodal-content" class="qrmodal-content"><span id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR du membre en façe de votre Caméra afin de l\'authentifier.</p><video class="cam_video" autoplay></video><br/></div></div>';
	// prepend theKid to the beginning of theParent
	formulaire.insertBefore(buttonQR, formulaire.firstChild);
	formulaire.insertBefore(divModal, formulaire.firstChild);
	// insertAfter(buttonQR, formulaire);
	document.getElementById('bouton_qr_launcher').addEventListener('click',
		function() {
			// modal.style.display = "block";
			initiateqr();
		}, false);
}

function outilqr_champcm_sansbouton(idform, id_champcm) {
	// '<button class="btn btn-primary">QR CODE</button>';
	var formulaire = document.getElementById(idform);
	var buttonQR = document.createElement("button");
	buttonQR.className = "btn btn-primary"
	buttonQR.innerHTML = 'QR CODE';
	buttonQR.setAttribute("id", "bouton_qr_launcher");
	var divModal = document.createElement("div");
	divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div id="qrmodal-content" class="qrmodal-content"><span id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR du membre en façe de votre Caméra afin de l\'authentifier.</p><video class="cam_video" autoplay></video><br/></div></div>';
	// prepend theKid to the beginning of theParent
	formulaire.insertBefore(buttonQR, formulaire.firstChild);
	formulaire.insertBefore(divModal, formulaire.firstChild);
	// insertAfter(buttonQR, formulaire);
	document.getElementById('bouton_qr_launcher').addEventListener('click',
		function() {
			// modal.style.display = "block";
			initiateqr(null, id_champcm, false);
		}, false);
}

function outilqrbouton(idform, idBouton) {
	// '<button class="btn btn-primary">QR CODE</button>';
	var formulaire = document.getElementById(idform);
	var buttonQR = document.getElementById(idBouton);
	// buttonQR.className = "btn btn-primary"
	// buttonQR.innerHTML = 'QR CODE';
	// buttonQR.setAttribute("id", "bouton_qr_launcher");
	var divModal = document.createElement("div");
	divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div id="qrmodal-content" class="qrmodal-content"><span id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR du membre en façe de votre Caméra afin de l\'authentifier.</p><video class="cam_video" autoplay></video><br/></div></div>';
	// prepend theKid to the beginning of theParent
	// formulaire.insertBefore(buttonQR, formulaire.firstChild);
	formulaire.insertBefore(divModal, formulaire.firstChild);
	// insertAfter(buttonQR, formulaire);
	document.getElementById(idBouton).addEventListener('click', function() {
		// modal.style.display = "block";
		initiateqr(null, null, false);
	}, false);
}

function outilqrformobligatoire(idform, idBoutonSubmit) {
	// '<button class="btn btn-primary">QR CODE</button>';
	document.getElementById(idBoutonSubmit).disabled = true;
	var formulaire = document.getElementById(idform);
	var buttonQR = document.createElement("button");
	buttonQR.className = "btn btn-primary"
	buttonQR.innerHTML = 'QR CODE';
	buttonQR.setAttribute("id", "bouton_qr_launcher");
	var divModal = document.createElement("div");
	divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div id="qrmodal-content" class="qrmodal-content"><span id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR du membre en façe de votre Caméra afin de l\'authentifier.</p><video class="cam_video" autoplay></video><br/></div></div>';
	// prepend theKid to the beginning of theParent
	formulaire.insertBefore(buttonQR, formulaire.firstChild);
	formulaire.insertBefore(divModal, formulaire.firstChild);
	// insertAfter(buttonQR, formulaire);
	document.getElementById('bouton_qr_launcher').addEventListener('click',
		function() {
			// modal.style.display = "block";
			initiateqr(idBoutonSubmit, null, true);
		}, false);
}

function outilqrbouton_cm(idform, idBouton, idChampCM) {
	// '<button class="btn btn-primary">QR CODE</button>';
	var formulaire = document.getElementById(idform);
	var buttonQR = document.getElementById(idBouton);
	// buttonQR.className = "btn btn-primary"
	// buttonQR.innerHTML = 'QR CODE';
	// buttonQR.setAttribute("id", "bouton_qr_launcher");
	var divModal = document.createElement("div");
	divModal.innerHTML = '<div id="myModal" class="qrmodal overlayDiv"><div id="qrmodal-content" class="qrmodal-content"><span id="close-qrmodal">&times;</span><br/> <br/><p>Veuillez mettre le code QR du membre en façe de votre Caméra afin de l\'authentifier.</p><video class="cam_video" autoplay></video><br/></div></div>';
	// prepend theKid to the beginning of theParent
	// formulaire.insertBefore(buttonQR, formulaire.firstChild);
	formulaire.insertBefore(divModal, formulaire.firstChild);
	// insertAfter(buttonQR, formulaire);
	document.getElementById(idBouton).addEventListener('click', function() {
		// modal.style.display = "block";
		initiateqr(null, idChampCM, false);
	}, false);
}

function outilqr_terminal_auth(cm, nc) {
	// console.log('cm'+cm);
	// console.log('nc'+nc);
	var xhttpV2 = XHRFactory.getInstance();
	// var url = 'https://prod.gacsource.net/confirmation/check';
	var url = getBaseUrl() + '/confirmation/check';

	xhttpV2.open("POST", url, true);
	xhttpV2.setRequestHeader("Content-type",
		"application/x-www-form-urlencoded");

	xhttpV2.onreadystatechange = function() {
		if (xhttpV2.readyState == 4) {
			// if "OK"
			if (xhttpV2.status == 200) {
				// process the response
				console.log(xhttpV2.responseText);
				var responseObjV = JSON.parse(xhttpV2.responseText);
				nc

				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					// document.getElementById('status_check').innerHTML =
					// "<br/><center>En attente de la confirmation du
					// membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						outilqr_terminal_auth(cm, nc);
					}, 3000);

					var currenTimer = +new Date();
					var difference = currenTimer / 1000
						- responseObjV.date_creation;

					if (difference > 300) {
						// alert("Session expirée - Plus d'une minute")
						document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
						document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
						document.getElementById('swirl_icon').src = warningIconb64;
						document.getElementById('sms_confirmation').innerHTML = "Recommencer";
						document.getElementById('secondary_action').href = getBaseUrl() + '/terminal/logout';
						XHRFactory.release(xhttpV2);
					}
				}

				if (responseObjV.status == "2") {
					// alert("verified");
					document.getElementById('status_message_title').innerHTML = "Vérification éffectuée";
					document.getElementById('status_message_text').innerHTML = "Vous serez redirigé dans votre espace terminal d'echange dans un moment.";
					document.getElementById('swirl_icon').src = checkIconb64;
					// document.getElementById('sms_confirmation').style.display
					// = "none";
					document.getElementById('sms_confirmation').innerHTML = "Continuer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/terminal/';

					var timeout = 5; // in seconds

					var timeoutInterval = setInterval(
						function() {

							timeout--;

							document.getElementById('sms_confirmation').innerHTML = "Continuer ("
								+ timeout + ")";

							if (timeout == 0) {
								clearInterval(timeoutInterval);
								// redirect(strUrl);
								document.location = getBaseUrl() + '/terminal/';
							}

						}, 1000);

					// document.getElementById('status_check').innerHTML =
					// "<br/><center><strong>Confirmation éffectuée
					// !</strong><br/> Veuillez procéder à la suite de votre
					// opération.</center>";
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "3") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la vérification";
					document.getElementById('status_message_text').innerHTML = "La confirmation a été déclinée sur l'un de vos appareils.";
					document.getElementById('swirl_icon').src = alertIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/terminal/logout';
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "4") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
					document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
					document.getElementById('swirl_icon').src = warningIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/terminal/logout';
					XHRFactory.release(xhttpV2);
				}
			}

		}
	};
	xhttpV2.send("id_verification=" + nc);

}

function outilqr_personnel_auth(cm, nc) {
	// console.log('cm'+cm);
	// console.log('nc'+nc);

	var xhttpV2 = XHRFactory.getInstance();
	// var url = 'https://prod.gacsource.net/confirmation/check';
	var url = getBaseUrl() + '/confirmation/check';

	xhttpV2.open("POST", url, true);
	xhttpV2.setRequestHeader("Content-type",
		"application/x-www-form-urlencoded");

	xhttpV2.onreadystatechange = function() {
		if (xhttpV2.readyState == 4) {
			// if "OK"
			if (xhttpV2.status == 200) {
				// process the response
				console.log(xhttpV2.responseText);
				var responseObjV = JSON.parse(xhttpV2.responseText);
				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					// document.getElementById('status_check').innerHTML =
					// "<br/><center>En attente de la confirmation du
					// membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						outilqr_personnel_auth(cm, nc);
					}, 3000);

					var currenTimer = +new Date();
					var difference = currenTimer / 1000
						- responseObjV.date_creation;

					if (difference > 300) {
						// alert("Session expirée - Plus d'une minute")

						document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
						document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
						document.getElementById('swirl_icon').src = warningIconb64;
						document.getElementById('sms_confirmation').innerHTML = "Recommencer";
						document.getElementById('secondary_action').href = getBaseUrl() + '/integrateur/logout';
						XHRFactory.release(xhttpV2);

					}
				}

				if (responseObjV.status == "2") {
					// alert("verified");
					document.getElementById('status_message_title').innerHTML = "Vérification éffectuée";
					document.getElementById('status_message_text').innerHTML = "Vous serez redirigé dans votre espace personnel ou professionnel dans un moment.";
					document.getElementById('swirl_icon').src = checkIconb64;
					// document.getElementById('sms_confirmation').style.display
					// = "none";
					document.getElementById('sms_confirmation').innerHTML = "Continuer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/espacepersonnel/';

					var timeout = 5; // in seconds

					var timeoutInterval = setInterval(
						function() {

							timeout--;
							document.getElementById('sms_confirmation').innerHTML = "Continuer ("
								+ timeout + ")";

							if (timeout == 0) {
								clearInterval(timeoutInterval);
								// redirect(strUrl);
								document.location = getBaseUrl() + '/espacepersonnel/';
							}
						}, 1000);

					// document.getElementById('status_check').innerHTML =
					// "<br/><center><strong>Confirmation éffectuée
					// !</strong><br/> Veuillez procéder à la suite de votre
					// opération.</center>";
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "3") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la vérification";
					document.getElementById('status_message_text').innerHTML = "La confirmation a été déclinée sur l'un de vos appareils.";
					document.getElementById('swirl_icon').src = alertIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/espacepersonnel/logout';
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "4") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
					document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
					document.getElementById('swirl_icon').src = warningIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/espacepersonnel/logout';
					XHRFactory.release(xhttpV2);
				}
			}

		}
	};
	xhttpV2.send("id_verification=" + nc);
}

function outilqr_administration_auth(cm, nc) {
	// console.log('cm'+cm);
	// console.log('nc'+nc);

	var xhttpV2 = XHRFactory.getInstance();
	var url = getBaseUrl() + '/confirmation/check';
	// var url = document.location.origin+'/confirmation/check';

	xhttpV2.open("POST", url, true);
	xhttpV2.setRequestHeader("Content-type",
		"application/x-www-form-urlencoded");

	xhttpV2.onreadystatechange = function() {
		if (xhttpV2.readyState == 4) {
			// if "OK"
			if (xhttpV2.status == 200) {
				// process the response
				console.log(xhttpV2.responseText);
				var responseObjV = JSON.parse(xhttpV2.responseText);

				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					// document.getElementById('status_check').innerHTML =
					// "<br/><center>En attente de la confirmation du
					// membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						outilqr_administration_auth(cm, nc);
					}, 3000);

					var currenTimer = +new Date();
					var difference = currenTimer / 1000
						- responseObjV.date_creation;

					if (difference > 300) {
						// alert("Session expirée - Plus d'une minute")

						document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
						document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
						document.getElementById('swirl_icon').src = warningIconb64;
						document.getElementById('sms_confirmation').innerHTML = "Recommencer";
						document.getElementById('secondary_action').href = getBaseUrl() + '/integrateur/logout';
						XHRFactory.release(xhttpV2);

					}
				}

				if (responseObjV.status == "2") {

					// alert("verified");
					document.getElementById('status_message_title').innerHTML = "Vérification éffectuée";
					document.getElementById('status_message_text').innerHTML = "Vous serez redirigé dans votre zone d'administration dans un moment.";
					document.getElementById('swirl_icon').src = checkIconb64;
					// document.getElementById('sms_confirmation').style.display
					// = "none";
					document.getElementById('sms_confirmation').innerHTML = "Continuer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/administration/';

					var timeout = 5; // in seconds

					var timeoutInterval = setInterval(
						function() {

							timeout--;

							document.getElementById('sms_confirmation').innerHTML = "Continuer ("
								+ timeout + ")";

							if (timeout == 0) {
								clearInterval(timeoutInterval);
								// redirect(strUrl);
								document.location = getBaseUrl() + '/administration/';
							}

						}, 1000);

					// document.getElementById('status_check').innerHTML =
					// "<br/><center><strong>Confirmation éffectuée
					// !</strong><br/> Veuillez procéder à la suite de votre
					// opération.</center>";
					XHRFactory.release(xhttpV2);
				}
				if (responseObjV.status == "3") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la vérification";
					document.getElementById('status_message_text').innerHTML = "La confirmation a été déclinée sur l'un de vos appareils.";
					document.getElementById('swirl_icon').src = alertIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/administration/logout';
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "4") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
					document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
					document.getElementById('swirl_icon').src = warningIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/administration/logout';
					XHRFactory.release(xhttpV2);
				}
			}

		}
	};
	xhttpV2.send("id_verification=" + nc);

}

function outilqr_integrateur_auth(cm, nc) {
	// console.log('cm'+cm);
	// console.log('nc'+nc);

	var xhttpV2 = XHRFactory.getInstance();
	var url = getBaseUrl() + '/confirmation/check';
	// var url = document.location.origin+'/confirmation/check';

	xhttpV2.open("POST", url, true);
	xhttpV2.setRequestHeader("Content-type",
		"application/x-www-form-urlencoded");

	xhttpV2.onreadystatechange = function() {
		if (xhttpV2.readyState == 4) {
			// if "OK"
			if (xhttpV2.status == 200) {
				// process the response
				console.log(xhttpV2.responseText);
				var responseObjV = JSON.parse(xhttpV2.responseText);

				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					// document.getElementById('status_check').innerHTML =
					// "<br/><center>En attente de la confirmation du
					// membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						outilqr_integrateur_auth(cm, nc);
					}, 3000);

					var currenTimer = +new Date();
					var difference = currenTimer / 1000
						- responseObjV.date_creation;

					if (difference > 300) {
						// alert("Session expirée - Plus d'une minute")

						document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
						document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
						document.getElementById('swirl_icon').src = warningIconb64;
						document.getElementById('sms_confirmation').innerHTML = "Recommencer";
						document.getElementById('secondary_action').href = getBaseUrl() + '/integrateur/logout';
						XHRFactory.release(xhttpV2);

					}

				}

				if (responseObjV.status == "2") {

					// alert("verified");
					document.getElementById('status_message_title').innerHTML = "Vérification éffectuée";
					document.getElementById('status_message_text').innerHTML = "Vous serez redirigé dans votre espace intégrateur dans un moment.";
					document.getElementById('swirl_icon').src = checkIconb64;
					// document.getElementById('sms_confirmation').style.display
					// = "none";
					document.getElementById('sms_confirmation').innerHTML = "Continuer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/integrateur/';

					var timeout = 5; // in seconds

					var timeoutInterval = setInterval(
						function() {

							timeout--;

							document.getElementById('sms_confirmation').innerHTML = "Continuer ("
								+ timeout + ")";

							if (timeout == 0) {
								clearInterval(timeoutInterval);
								// redirect(strUrl);
								document.location = getBaseUrl() + '/integrateur/';
							}

						}, 1000);

					// document.getElementById('status_check').innerHTML =
					// "<br/><center><strong>Confirmation éffectuée
					// !</strong><br/> Veuillez procéder à la suite de votre
					// opération.</center>";
					XHRFactory.release(xhttpV2);
				}
				if (responseObjV.status == "3") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la vérification";
					document.getElementById('status_message_text').innerHTML = "La confirmation a été déclinée sur l'un de vos appareils.";
					document.getElementById('swirl_icon').src = alertIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/integrateur/logout';
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "4") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
					document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
					document.getElementById('swirl_icon').src = warningIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = document.location.origin
						+ '/integrateur/logout';
					XHRFactory.release(xhttpV2);
				}
			}

		}
	};

	xhttpV2.send("id_verification=" + nc);

}

function outilqr_banque_auth(cm, nc) {
	// console.log('cm'+cm);
	// console.log('nc'+nc);

	var xhttpV2 = XHRFactory.getInstance();
	var url = getBaseUrl() + '/confirmation/check';
	// var url = document.location.origin+'/confirmation/check';

	xhttpV2.open("POST", url, true);
	xhttpV2.setRequestHeader("Content-type",
		"application/x-www-form-urlencoded");

	xhttpV2.onreadystatechange = function() {
		if (xhttpV2.readyState == 4) {
			// if "OK"
			if (xhttpV2.status == 200) {
				// process the response
				console.log(xhttpV2.responseText);
				var responseObjV = JSON.parse(xhttpV2.responseText);

				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					// document.getElementById('status_check').innerHTML =
					// "<br/><center>En attente de la confirmation du
					// membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						outilqr_banque_auth(cm, nc);
					}, 3000);

					var currenTimer = +new Date();
					var difference = currenTimer / 1000
						- responseObjV.date_creation;

					if (difference > 300) {
						// alert("Session expirée - Plus d'une minute")

						document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
						document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
						document.getElementById('swirl_icon').src = warningIconb64;
						document.getElementById('sms_confirmation').innerHTML = "Recommencer";
						document.getElementById('secondary_action').href = getBaseUrl() + '/integrateur/logout';
						XHRFactory.release(xhttpV2);

					}
				}

				if (responseObjV.status == "2") {
					// alert("verified");
					document.getElementById('status_message_title').innerHTML = "Vérification éffectuée";
					document.getElementById('status_message_text').innerHTML = "Vous serez redirigé dans votre espace banque dans un moment.";
					document.getElementById('swirl_icon').src = checkIconb64;
					// document.getElementById('sms_confirmation').style.display
					// = "none";
					document.getElementById('sms_confirmation').innerHTML = "Continuer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/banqueopi/';

					var timeout = 5; // in seconds

					var timeoutInterval = setInterval(
						function() {

							timeout--;

							document.getElementById('sms_confirmation').innerHTML = "Continuer ("
								+ timeout + ")";

							if (timeout == 0) {
								clearInterval(timeoutInterval);
								// redirect(strUrl);
								document.location = getBaseUrl() + '/banqueopi/';
							}

						}, 1000);

					// document.getElementById('status_check').innerHTML =
					// "<br/><center><strong>Confirmation éffectuée
					// !</strong><br/> Veuillez procéder à la suite de votre
					// opération.</center>";
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "3") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la vérification";
					document.getElementById('status_message_text').innerHTML = "La confirmation a été déclinée sur l'un de vos appareils.";
					document.getElementById('swirl_icon').src = alertIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/banqueopi/logout';
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "4") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
					document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
					document.getElementById('swirl_icon').src = warningIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/banqueopi/logout';
					XHRFactory.release(xhttpV2);
				}
			}

		}
	};
	xhttpV2.send("id_verification=" + nc);

}

function outilqr_espacepersonnel_auth(cm, nc) {

	var xhttpV2 = XHRFactory.getInstance();
	// var url = document.location.origin+'/confirmation/check';
	var url = getBaseUrl() + '/confirmation/check';
	xhttpV2.open("POST", url, true);
	xhttpV2.setRequestHeader("Content-type",
		"application/x-www-form-urlencoded");

	xhttpV2.onreadystatechange = function() {
		if (xhttpV2.readyState == 4) {
			// if "OK"
			if (xhttpV2.status == 200) {
				// process the response
				console.log(xhttpV2.responseText);
				var responseObjV = JSON.parse(xhttpV2.responseText);

				// console.log('Status '+responseObjV.status);
				if (responseObjV.status == "1") {
					// document.getElementById('status_check').innerHTML =
					// "<br/><center>En attente de la confirmation du
					// membre</center>";
					// console.log('Status 1 - Retry');
					setTimeout(function() {
						outilqr_espacepersonnel_auth(cm, nc);
					}, 3000);

					var currenTimer = +new Date();
					var difference = currenTimer / 1000
						- responseObjV.date_creation;

					if (difference > 300) {
						// alert("Session expirée - Plus d'une minute")

						document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
						document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
						document.getElementById('swirl_icon').src = warningIconb64;
						document.getElementById('sms_confirmation').innerHTML = "Recommencer";
						document.getElementById('secondary_action').href = getBaseUrl() + '/index/logout';
						XHRFactory.release(xhttpV2);

					}
				}

				if (responseObjV.status == "2") {

					// alert("verified");
					document.getElementById('status_message_title').innerHTML = "Vérification éffectuée";
					document.getElementById('status_message_text').innerHTML = "Vous serez redirigé dans votre zone d'espacepersonnel dans un moment.";
					document.getElementById('swirl_icon').src = checkIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Continuer";
					document.getElementById('secondary_action').href = getBaseUrl()
						+ '/espacepersonnel/';

					var timeout = 5; // in seconds

					var timeoutInterval = setInterval(
						function() {

							timeout--;

							document.getElementById('sms_confirmation').innerHTML = "Continuer ("
								+ timeout + ")";

							if (timeout == 0) {
								clearInterval(timeoutInterval);
								// redirect(strUrl);
								document.location = getBaseUrl() + '/espacepersonnel/';
							}

						}, 1000);

					// document.getElementById('status_check').innerHTML =
					// "<br/><center><strong>Confirmation éffectuée
					// !</strong><br/> Veuillez procéder à la suite de votre
					// opération.</center>";
					XHRFactory.release(xhttpV2);
				}
				if (responseObjV.status == "3") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la vérification";
					document.getElementById('status_message_text').innerHTML = "La confirmation a été déclinée sur l'un de vos appareils.";
					document.getElementById('swirl_icon').src = alertIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/espacepersonnel/logout';
					XHRFactory.release(xhttpV2);
				}

				if (responseObjV.status == "4") {
					// alert("declined");
					document.getElementById('status_message_title').innerHTML = "Echec de la confirmation";
					document.getElementById('status_message_text').innerHTML = "La session a expiré car vous avez mis trop de temps à éffectuer la confirmation biométrique";
					document.getElementById('swirl_icon').src = warningIconb64;
					document.getElementById('sms_confirmation').innerHTML = "Recommencer";
					document.getElementById('secondary_action').href = getBaseUrl() + '/index/logout';
					XHRFactory.release(xhttpV2);
				}
			}

		}
	};
	xhttpV2.send("id_verification=" + nc);

}

function outilqr_champ_personnalise(bouton_qr, champ_cm, champ_nom) {
	var modal = document.getElementById('myModal');
	// var champCm = document.getElementById('button_qr_code');

	// startQRCameraMode2(idBoutonSubmit, idChampCM, obligation);

	document.getElementById('close-qrmodal').addEventListener('click',
		function() {
			modal.style.display = "none";
			qr.stop();
		}, false);

	document
		.getElementById(bouton_qr)
		.addEventListener(
			'click',
			function() {
				// modal.style.display = "block";
				// alert("fsdfdfdsfsq"+bouton_qr);
				modal.style.display = "block";

				var qr = new QCodeDecoder();
				if (!(qr.isCanvasSupported() && qr.hasGetUserMedia())) {
					alert('Your browser doesn\'t match the required specs.');
					throw new Error(
						'Canvas and getUserMedia are required');
				}

				var video = document.querySelector('video');
				var reset = document.querySelector('#reset');
				var stop = document.querySelector('#stop');

				function resultHandler(err, result) {

					if (err) {
						return console.log(err.message);
					}

					var str = result;
					var infosqr = str.split(',');
					qr.stop();

					// alert("resultat");
					if (infosqr[0].length != 20) {
						alert("Ce code membre est incorret !");
					} else {
						// alert("Code membre obtenu");
						document.getElementById('myModal').innerHTML = '<div id="minispinner"></div>';

						var code_membre_actuel = infosqr[0];
						// var xhttpINFO = new XMLHttpRequest();
						var xhttpINFO = XHRFactory.getInstance();

						xhttpINFO.onreadystatechange = function() {
							// console.log(this.responseText);
							if (xhttpINFO.readyState == 4
								&& xhttpINFO.status == 200) {

								var responseObjINFO = JSON
									.parse(this.responseText);
								if (responseObjINFO.code_membre.length == 20) {
									document.getElementById('myModal').style.display = "none";
									console.log(this.responseText);

									if (responseObjINFO.code_status == 0) {
										if (document
											.getElementById(champ_cm)) {
											document
												.getElementById(champ_cm).value = responseObjINFO.code_membre;
											document.getElementById(
												champ_cm).focus();
										}

										// Si c'est une personne
										// physique
										if (responseObjINFO.code_membre
											.slice(-1) == "P") {
											if (document
												.getElementById(champ_nom)) {
												document
													.getElementById(champ_nom).value = responseObjINFO.nom_membre
													+ " "
													+ responseObjINFO.prenom_membre;
											}
										}
										// Si c'est une personne morale
										if (responseObjINFO.code_membre
											.slice(-1) == "M") {
											if (document
												.getElementById(champ_nom)) {
												document
													.getElementById(champ_nom).value = responseObjINFO.raison_sociale;
											}
										}

									} else {
										alert("Ce compte a été désactivé. Veuillez activer votre compte afin de pouvoir faire cette opération.");
										console
											.log(responseObjINFO.message);
									}

								}
							}
						};
						xhttpINFO.open("POST", getBaseUrl() + "/api/qrinfos", true);
						xhttpINFO.setRequestHeader("Content-type",
							"application/x-www-form-urlencoded");
						xhttpINFO.send("code_membre="
							+ code_membre_actuel);

					}
				}

				qr.decodeFromCamera(video, resultHandler, true);

			}, false);
}
