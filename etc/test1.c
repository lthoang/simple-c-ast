
int list[500];
void swap(int a, int b);
void quickSort(int start, int end);
void swap(int a, int b) 
{
  int temp;
  temp = list[a];
  list[a] = list[b];
  list[b] = temp;
}

void quickSort(int start, int end) 
{
  int lower;
  int upper;
  lower = start + 1;
  upper = end;
  swap(start, (start + end) / 2);
  while (lower <= upper) 
  {
    while (list[lower] < list[start]) 
	{
      lower = lower + 1;
	}
    while (list[start] < list[upper]) 
	{
      upper = upper - 1;
    }
	if (lower < upper) 
	{
      swap(lower, upper);
      lower = lower + 1;
      upper = upper + 1;
    } 
	else 
	{
      lower = lower + 1;
    }
  }
  swap(upper, start);
  if (start < upper - 1) 
  {
    quickSort(start, upper - 1);
  }
  if (upper + 1 < end) 
  {
    quickSort(upper + 1, end);
  }
}

void main() 
{
  int length;
  int i;
  length = 500;
  
  if (length >= 2) 
  {
    int max;
    max = 0;
    
    /*
    i = 1;
    while (i < length) {
      if (list[max] < list[i]) {
        max = i;
      }
      i = i + 1;
    }
    */
    for(i=1; i<length; i=i+1)
    {
      if (list[max] < list[i]) 
	  {
         max = i;
      }
    }
    swap(length - 1, max);
    quickSort(0, length - 2);
  }
}
