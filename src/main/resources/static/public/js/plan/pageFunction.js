
class CanvasCreate {
    index;
    canvas; ctx;
    layerArr = []; //레이어 구현용 배열
    activatedTool; //활성화 툴 체크용
    pageSize = 200/1200; //페이지 사이즈 변화에 따른 배율 조졍용(아직)
    currentScale = 1.0; // 줌기능 배율 조졍용
    currentCanvas = new Image(); //배율조정시 리로드용
    defaultBack = new Image();

    constructor() {
        // 캔버스 본체
        this.canvas = document.createElement("canvas");
        this.ctx = this.canvas.getContext("2d")

        // 캔버스 초기화
        this.init()
    }
    init(){
        // 스타일 관련 초기 설정
        // this.index = ++canvasCnt;
        this.canvas.width = 1200;
        this.canvas.height = 500;
        this.ctx.strokeStyle = "black";
        this.ctx.lineWidth = 0.5;
        this.ctx.scale(1.0,1.0)
        this.ctx.translate(0,0)
        this.ctx.setLineDash([])
        this.ctx.save();

        // 버튼활성화 및 배경디자인 생성
        this.pageLiner(this.canvas.width,this.canvas.height)

        webSocket.onmessage=function (e){
            console.log(e)
        }
        document.getElementById("stampBtn").addEventListener("click",()=>{
            this.stampTool();
        })
    }


    stampTool() {
        let canvasObj = this;
        let moveHandler;
        function startFunction(e) {

            let startX = e.offsetX;
            let startY = e.offsetY;
            let pathArray = [];
            canvasObj.ctx.beginPath()
            canvasObj.ctx.moveTo(e.offsetX,e.offsetY);
            this.addEventListener("mousemove", moveHandler = function (e){

                canvasObj.ctx.lineTo(e.offsetX,e.offsetY);
                canvasObj.ctx.stroke()
            })
            this.addEventListener("mouseup",(e)=>{
                this.removeEventListener("mousemove", moveHandler)
            },{once:true})
        }

        this.canvas.addEventListener("mousedown", startFunction);
    };

    // 캔버스 기본 배경
    pageLiner(width,height){
        let x = 0;
        let y = 0;
        while (x<=height){
            this.ctx.beginPath();
            if(x===0 || height-x===0){
                this.ctx.strokeStyle="black"
                this.ctx.lineWidth=1.0;
            } else if(x%50===0){
                this.ctx.strokeStyle="gray"
                this.ctx.lineWidth=0.2;
            } else{
                this.ctx.strokeStyle="gray"
                this.ctx.lineWidth=0.1;
            }
            this.ctx.moveTo(0, x);
            this.ctx.lineTo(width, x);
            this.ctx.stroke();
            x+=10;
        }
        while (y<=width){
            this.ctx.beginPath();
            if(y===0 || y===width){
                this.ctx.strokeStyle="black"
                this.ctx.lineWidth=1.0;
            } else if(y%50===0){
                if(y/50===12){
                    this.ctx.strokeStyle="red"
                    this.ctx.lineWidth=0.3;
                } else {
                    this.ctx.strokeStyle="gray"
                    this.ctx.lineWidth=0.2;
                }
            } else{
                this.ctx.strokeStyle="gray"
                this.ctx.lineWidth=0.1;
            }
            this.ctx.moveTo(y, 0);
            this.ctx.lineTo(y, height);
            this.ctx.stroke();
            y+=10;
        }
        this.ctx.restore()
        this.ctx.save()
    }
}
