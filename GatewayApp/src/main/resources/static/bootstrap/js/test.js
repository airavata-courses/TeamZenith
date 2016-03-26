        $(document).ready(function (){
            $("#jobType").change(function() {
                // foo is the id of the other select box 
                if ($(this).val() == "cust") {
                    $(".radio").show();
                    $(".custombox").show();
                    $(".gromacsbox").hide();


                }else{
                    $(".radio").hide();
                    $(".gromacsbox").show();
                    $(".custombox").hide();


                } 
            });
        });