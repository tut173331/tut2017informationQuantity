package s4.b173331; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;

public class Frequencer implements FrequencerInterface{
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;

    int [] suffixArray;


    private void printSuffixArray(){
        if(spaceReady) {
            int cnt=0;
            for(int i=0; i<mySpace.length; i++){
            int s = suffixArray[i];
            if(cnt<10)
                System.out.print("0");
            System.out.print(cnt+":");
            cnt++;
            for(int j=s; j<mySpace.length; j++){
                System.out.write(mySpace[j]);
            }
            System.out.write('\n');
            }
        }
    }

    private int suffixCompare(int i,int j) {
        int si = suffixArray[i];
        int sj = suffixArray[j];
        int s = 0;
        if(si > s) s = si;
        if(sj > s) s = sj;
        int n = mySpace.length -s;
        for(int k = 0; k<n; k++){
            if(mySpace[si+k]>mySpace[sj+k]) return 1;
            if(mySpace[si+k]<mySpace[sj+k]) return -1;
        }
        if(si < sj) return 1;
        if(si > sj) return -1;
        return 0;
    }

    public void setSpace(byte[] space) {
        mySpace = space;
        if(mySpace.length>0)
            spaceReady = true;
        suffixArray = new int[space.length];
        for(int i = 0; i < space.length; i++){
            suffixArray[i] = i;
        }
        quick_sort(0,mySpace.length-1);
        printSuffixArray();
    }
    
    public int part(int low,int high)
    {
        int pivot=suffixArray[low];
        int leftwall=low;
        for(int i=low+1;i<=high;i++)
        {
            if(suffixCompare(i,low)==-1){
                leftwall++;
                int tmp=suffixArray[i];
                suffixArray[i]=suffixArray[leftwall];
                suffixArray[leftwall]=tmp;
            }
        }
        int tmp=suffixArray[low];
        suffixArray[low]=suffixArray[leftwall];
        suffixArray[leftwall]=tmp;
        
        return leftwall;
    }
    
    public void quick_sort(int low,int high){
        if(low<high){
            int pivot_location=part(low,high);
            quick_sort(low,pivot_location);
            quick_sort(pivot_location+1,high);
        }
    }

    private int targetCompare(int i,int start,int end){
        int sa = suffixArray[i];
        int ms = end-start;
        
        for(int k = 0; k < ms; k++){
            if(sa+k<mySpace.length){
                if(mySpace[sa+k] > myTarget[start+k]) return 1;
                if(mySpace[sa+k] < myTarget[start+k]) return -1;
            }
            else
                return -1;
        }
        //if(ms > mySpace.length-sa) return -1;//targetの方が長いときの処理
       
        return 0;
    }
    
    private int subByteStartIndex(int start,int end){
        /*for(int i=0;i<mySpace.length;i++){
            if(targetCompare(i,start,end)==0){
                return i;
            }
        }*/
        
        
        
        //System.out.println("-----subByteStartIndex-----");
        int left=0;
        int right=suffixArray.length-1;

        while(right-left>1){
            int mid=(left+right)/2;
            /*System.out.println("mid="+mid);
            System.out.println("left="+left);
            System.out.println("right="+right);
            System.out.println("======================");*/
            if(targetCompare(mid,start,end)==0)
            {
                right=mid;
                int num=mid;
                //while(left<right){
                while(right-left>1){
                    mid=(left+right)/2;
                    /*System.out.println("mid="+mid);
                    System.out.println("left="+left);
                    System.out.println("right="+right);
                    System.out.println("num="+num);
                    System.out.println("--------------------------");*/
                    if(targetCompare(mid,start,end)==0){
                        if(mid<num){
                            num=mid;
                            right=mid-1;
                        }
                        
                    }
                    else if(targetCompare(mid,start,end)==-1){
                        left=mid;
                        
                    }
                    else if(targetCompare(mid,start,end)==1){
                        right=mid-1;
                    }
                }
                if(targetCompare(0,start,end)==0)
                    return 0;
                if(targetCompare(left,start,end)==0)
                    return left;
                else if(targetCompare(right,start,end)==0)
                    return right;
                return num;
            }
            else if(targetCompare(mid,start,end)==-1){
                left=mid+1;
            }
            else if(targetCompare(mid,start,end)==1){
                right=mid-1;
            }
            
        }
        if(targetCompare(left,start,end)==0)
            return left;
        else if(targetCompare(right,start,end)==0)
            return right;
        return suffixArray.length;
        

    }

    private int subByteEndIndex(int start,int end){
        /*for(int i=mySpace.length-1; i > 0 ;i--){
            if(targetCompare(i,start,end)==0)
                return i+1;
        }*/
        
        
        //System.out.println("-----subByteEndIndex-----");
        int left=0;
        int right=mySpace.length-1;
        while(right-left>1){
            int mid=(left+right)/2;
            /*System.out.println("mid="+mid);
            System.out.println("left="+left);
            System.out.println("right="+right);
            System.out.println("======================");*/
            if(targetCompare(mid,start,end)==0)
            {
                left=mid;
                int num=mid;
                //while(left<right){
                while(right-left>1){
                    mid=(left+right)/2;
                    /*System.out.println("mid="+mid);
                    System.out.println("left="+left);
                    System.out.println("right="+right);
                    System.out.println("num="+num);
                    System.out.println("--------------------------");*/
                    if(targetCompare(mid,start,end)==0){
                        if(mid>num){
                            num=mid;
                            left=mid+1;
                        }
                        
                    }
                    else if(targetCompare(mid,start,end)==-1){
                        left=mid+1;
                        
                    }
                    else if(targetCompare(mid,start,end)==1){
                        right=mid-1;
                    }
                }
                /*System.out.println("mid="+mid);
                System.out.println("left="+left);
                System.out.println("right="+right);
                System.out.println("num="+num);
                System.out.println("--------------------------");*/
                if(targetCompare(suffixArray.length-1,start,end)==0)
                    return suffixArray.length;
                if(targetCompare(right,start,end)==0)
                    return right+1;
                else if(targetCompare(left,start,end)==0)
                    return left+1;
                return num+1;
            }
            else if(targetCompare(mid,start,end)==-1)
                left=mid+1;
                
            else if(targetCompare(mid,start,end)==1)
                right=mid-1;
        }
        if(targetCompare(right,start,end)==0)
            return right+1;
        else if(targetCompare(left,start,end)==0)
            return left+1;
        return suffixArray.length;
    }
    
    public int subByteFrequency(int start,int end){
        int spaceLength = mySpace.length;
        int count = 0;
        for(int offset = 0; offset<spaceLength - (end - start); offset++){
            boolean abort = false;
            for(int i=0; i< (end-start); i++){
                if(myTarget[start+i] != mySpace[offset+i])
                {
                    abort = true;
                    break;
                }
            }
            if(abort == false)
            {
                count++;
            }
        }
        int first = subByteStartIndex(start,end);
        int last1 = subByteEndIndex(start,end);
        for(int k=start;k<end;k++){
            System.out.write(myTarget[k]);
        }
        System.out.printf(":first=%d last1=%d\n",first,last1);
        return last1-first;
    }

    public void setTarget(byte[] target) {
        myTarget = target;
        if(myTarget.length>0)
            targetReady=true;
     }

    public int frequency() {
        if(targetReady==false)return -1;
        if(spaceReady==false)return 0;
        return subByteFrequency(0,myTarget.length);
    }
    

    public static void main(String[] args) {
        Frequencer myObject;
        int freq;
        try {
            System.out.println("checking my Frequencer");
            myObject = new Frequencer();
            myObject.setSpace("Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho".getBytes());
	    
            myObject.setTarget("Hi Ho".getBytes());
            freq = myObject.frequency();
            System.out.print("\"H\" in \"Hi Ho Hi Ho\" appears "+freq+" times. ");
            if(4 == freq) { System.out.println("OK"); } else {System.out.println("WRONG"); }
            }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
            }
        }
}	  
