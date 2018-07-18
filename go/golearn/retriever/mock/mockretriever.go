package mock

import "fmt"

type Retriever struct {
	Contents string
}

//类似与tostring
func (r *Retriever) String() string {
	return fmt.Sprintf(
		"Retriever: {Contents=%s}", r.Contents)
}

func (r *Retriever) Post(url string,
	form map[string]string) string {
		fmt.Fprint()
	r.Contents = form["contents"]
	return "ok"
}

func (r *Retriever) Get(url string) string {
	return r.Contents
}
