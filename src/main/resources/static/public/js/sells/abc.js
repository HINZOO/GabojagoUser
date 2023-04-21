async function removeBtn(cartId){
    let url=`/cart/${cartId}/remove.do`;
    const resp=await fetch(url,{method:"DELETE"});
    if (resp.status===200){
    const result=await resp.json();
        if (json.remove()>0){
            alert("상제성공")
        }
    }

}

async function bookBtn(sId,btn){
    let url=`/follow/${sId}/handler.do`
    const resp=await fetch(url,{method:"POST"});
    if(resp.status===200){
        let status=await resp.text(); //"0" or "1"
        return status;
    }else if(resp.status===400){
        alert("로그인 하셔야 이용할 수 있는 서비스 입니다.");
    }else if(resp.status===500){//팔로잉 했는데 한번더 팔로잉하면 db 오류 발생 or db 통신오류
        alert("이미 팔로잉되었거나 오류가 발생했으니 새로고침하고 다시 시도하세요~");
    }
}