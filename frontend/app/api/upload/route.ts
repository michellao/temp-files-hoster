import proxyBackend from "@/lib/proxy-backend";

export async function POST(request: Request) {
  return proxyBackend(request);
}
