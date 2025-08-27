import proxyBackend from "@/lib/proxy-backend";

export async function GET(
  request: Request,
  {
    params,
  }: {
    params: Promise<{ slug: string }>;
  },
) {
  const { slug } = await params;
  return proxyBackend(request, slug);
}

export async function DELETE(
  request: Request,
  {
    params,
  }: {
    params: Promise<{ slug: string }>;
  },
) {
  const { slug } = await params;
  const modifiedRequest = new Request(request, {
    method: "POST",
  });
  return proxyBackend(modifiedRequest, slug);
}
